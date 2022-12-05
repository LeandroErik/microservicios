package ar.utn.credicoop.msventas;

import ar.utn.credicoop.msventas.modelos.Comprador;
import ar.utn.credicoop.msventas.modelos.Item;
import ar.utn.credicoop.msventas.modelos.ItemDTO;
import ar.utn.credicoop.msventas.modelos.Vendedor;
import ar.utn.credicoop.msventas.modelos.carrito.CarritoDeCompra;
import ar.utn.credicoop.msventas.modelos.carrito.EstadoCarrito;
import ar.utn.credicoop.msventas.modelos.carrito.PosibleEstadoCarrito;
import ar.utn.credicoop.msventas.modelos.compra.Compra;
import ar.utn.credicoop.msventas.modelos.compra.EstadoCompra;
import ar.utn.credicoop.msventas.modelos.compra.PosibleEstadoCompra;
import ar.utn.credicoop.msventas.modelos.publicacion.EstadoPublicacion;
import ar.utn.credicoop.msventas.modelos.publicacion.PosibleEstadoPublicacion;
import ar.utn.credicoop.msventas.modelos.publicacion.Publicacion;
import ar.utn.credicoop.msventas.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
public class VentasController {

    @Autowired
    ProductoPersonalizadoProxy proxy;

    @Autowired
    RepoPublicacion repoPublicacion;

    @Autowired
    RepoComprador repoComprador;

    @Autowired
    RepoVendedor repoVendedor;

    @Autowired
    RepoCarritoDeCompra repoCarrito;

    @Autowired
    RepoCompra repoCompra;

    @PostMapping("/vendedores/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> agregarVendedor(@RequestBody Vendedor vendedor){
        repoVendedor.save(vendedor);
        return ResponseEntity.ok("vendedorId: "+vendedor.getId());
    }

    @PostMapping("/compradores/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> agregarComprador(@RequestBody Comprador comprador){
        repoComprador.save(comprador);
        return ResponseEntity.ok("compradorId: "+comprador.getId());
    }

    @Transactional
    @PostMapping("/publicaciones/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> agregarPublicacion(@RequestBody PublicacionDTO publicacionDTO){
            Publicacion publicacion = new Publicacion();
            publicacion.setVendedor(publicacionDTO.getVendedor());
            publicacion.setProductoPersonalizadoId(publicacionDTO.getProductoPersonalizadoId());

        if(proxy.existeProductoPersonalizado(publicacion.getProductoPersonalizadoId())){
            publicacion.setFechaDePublicacion(LocalDate.now());
            publicacion.agregarEstados(new EstadoPublicacion(PosibleEstadoPublicacion.ACTIVA,LocalDate.now(),LocalTime.now()));

            repoPublicacion.save(publicacion);
            return ResponseEntity.ok("publicacionId: "+publicacion.getId());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    //---------------

    @PostMapping("/carritoDeCompra/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> crearCarritoDeCompra(@RequestBody CarritoDeCompra carritoDeCompra){

        carritoDeCompra.agregarEstado(new EstadoCarrito(PosibleEstadoCarrito.PENDIENTE, LocalDate.now(), LocalTime.now()));

        repoCarrito.save(carritoDeCompra);
        return ResponseEntity.ok("CarritoDeCompraId: "+ carritoDeCompra.getId());
    }

    @Transactional
    @PostMapping("/carritoDeCompra/{carritoDeCompraId}/items")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> agregarCarritoDeCompra(@PathVariable("carritoDeCompraId") Integer carritoDeCompraId,@RequestBody ItemDTO itemDTO){
        Optional<CarritoDeCompra> carritoDeCompraOptional = repoCarrito.findById(carritoDeCompraId);
        if(!carritoDeCompraOptional.isPresent()){
            return new ResponseEntity<Object>("No se pudo crear carrito", null, HttpStatus.NOT_FOUND);
        }
        Item itemPub = new Item();
        Publicacion publicacion = new Publicacion();
        publicacion.setFechaDePublicacion(LocalDate.now());
        publicacion.setVendedor(itemDTO.getPublicacion().getVendedor());
        publicacion.setProductoPersonalizadoId(itemDTO.getPublicacion().getProductoPersonalizadoId());
        publicacion.agregarEstados(new EstadoPublicacion(PosibleEstadoPublicacion.ACTIVA,LocalDate.now(),LocalTime.now()));
        itemPub.setPublicacion(publicacion);
        itemPub.setCantidad(itemDTO.getCantidad());

        Optional<CarritoDeCompra> carritoOp = repoCarrito.findById(carritoDeCompraId);
        CarritoDeCompra carrito= carritoOp.get();
        carrito.agregarItem(itemPub);

        return ResponseEntity.ok("Se agrego Item");
    }



    //TODO :la consulta a 3 instancias
    @GetMapping("/carritoDeCompra/{carritoDeCompraId}/itemsPrecioTotal")
    public @ResponseBody ResponseEntity<Object>  consultarPrecioTotal(@PathVariable("carritoDeCompraId") Integer carritoDeCompraId){
        Double precioTotalCarrito=0.0;
        Optional<CarritoDeCompra> carritoDeCompraOptional=repoCarrito.findById(carritoDeCompraId);
        //TODO:comporbar que exista el carrito de compra en la BD
        CarritoDeCompra carrito = carritoDeCompraOptional.get();

        List<Item> items = carrito.getItems();

        for(Item actual:items){
            RtaProductoPersonalizadoDTO respuesta = proxy.consultarProductoPersonalizado(actual.getPublicacion().getProductoPersonalizadoId());
            precioTotalCarrito+=respuesta.getPrecioFinal() * actual.getCantidad();
        }

        return ResponseEntity.ok("precio total de carrito "+carritoDeCompraId+": "+precioTotalCarrito);
    }

    //TODO :generar uNA ORDEN DE COMPRA con carrito y medio de pago
    @Transactional
    //@PostMapping("/ordenDeCompra/")
    @RequestMapping(value = "/ordenDeCompra/", method = RequestMethod.POST,headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> generarOrdenDeCompra(@RequestBody CompraDTO compraDTO){
        Compra compra = new Compra();
        Optional<CarritoDeCompra> carritoDeCompraOptional = repoCarrito.findById(compraDTO.getCarritoDeCompraId());
        //TODO:Agregar verificacion de exitencia de carrito de compra.
        CarritoDeCompra carrito = carritoDeCompraOptional.get();
        compra.setCarritoDeCompra(carrito);
        compra.setHora(LocalTime.now());
        compra.setFecha(LocalDate.now());
        compra.agregarEstado(new EstadoCompra(PosibleEstadoCompra.PENDIENTE,LocalDate.now(),LocalTime.now()));
        compra.setMedioDePago(compraDTO.getMedioDePago());
        compra.setDatosPago(compraDTO.getDatosPago());

        repoCompra.save(compra);
        return ResponseEntity.ok("OrdenDeCompraId: "+compra.getId());
    }


    //TODO:bajar un servicio y que funcionen los demas

}
