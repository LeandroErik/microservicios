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
    public void agregarVendedor(@RequestBody Vendedor vendedor){
        repoVendedor.save(vendedor);
    }

    @PostMapping("/compradores/")
    @ResponseStatus(HttpStatus.OK)
    public void agregarComprador(@RequestBody Comprador comprador){
        repoComprador.save(comprador);
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
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    //---------------

    @PostMapping("/carritoDeCompra/")
    @ResponseStatus(HttpStatus.OK)
    public void crearCarritoDeCompra(@RequestBody CarritoDeCompra carritoDeCompra){

        carritoDeCompra.agregarEstado(new EstadoCarrito(PosibleEstadoCarrito.PENDIENTE, LocalDate.now(), LocalTime.now()));

        repoCarrito.save(carritoDeCompra);
    }

    @Transactional
    @PostMapping("/carritoDeCompra/{carritoDeCompraId}/items")
    @ResponseStatus(HttpStatus.OK)
    public void agregarCarritoDeCompra(@PathVariable("carritoDeCompraId") Integer carritoDeCompraId,@RequestBody ItemDTO itemDTO){
        Item itemPub = new Item();
        itemPub.setPublicacion(itemDTO.getPublicacion());
        itemPub.setCantidad(itemDTO.getCantidad());

        Optional<CarritoDeCompra> carritoOp = repoCarrito.findById(carritoDeCompraId);
        CarritoDeCompra carrito= carritoOp.get();
        carrito.agregarItem(itemPub);

        //TODO:Devolver precio actualizado del carrito de compra
    }



    //TODO :la consulta a 3 instancias
    @GetMapping("/carritoDeCompra/{carritoDeCompraId}/itemsPrecioTotal")
    public Double  consultarPrecioTotal(@PathVariable("carritoDeCompraId") Integer carritoDeCompraId){
        Double precioTotalCarrito=0.0;
        Optional<CarritoDeCompra> carritoDeCompraOptional=repoCarrito.findById(carritoDeCompraId);
        //TODO:comporbar que exista el carrito de compra en la BD
        CarritoDeCompra carrito = carritoDeCompraOptional.get();

        List<Item> items = carrito.getItems();

        for(Item actual:items){
            RtaProductoPersonalizadoDTO respuesta = proxy.consultarProductoPersonalizado(actual.getPublicacion().getProductoPersonalizadoId());
            precioTotalCarrito+=respuesta.getPrecioFinal();
        }
        CarritoDeCompraDTO carritoDTO = new CarritoDeCompraDTO(carritoDeCompraId,precioTotalCarrito);
        return precioTotalCarrito;
    }

    //TODO :generar uNA ORDEN DE COMPRA con carrito y medio de pago
    @Transactional
    @PostMapping("/ordenDeCompra/")
    @ResponseStatus(HttpStatus.OK)
    public void generarOrdenDeCompra(@RequestBody CompraDTO compraDTO){
        Compra compra = new Compra();
        Optional<CarritoDeCompra> carritoDeCompraOptional = repoCarrito.findById(compraDTO.getCarritoDeCompraId());
        //TODO:Agregar verificacion de exitencia de carrito de compra.
        CarritoDeCompra carrito = carritoDeCompraOptional.get();
        compra.setCarritoDeCompra(carrito);
        compra.setHora(LocalTime.now());
        compra.setFecha(LocalDate.now());
        compra.agregarEstado(new EstadoCompra(PosibleEstadoCompra.PENDIENTE,LocalDate.now(),LocalTime.now()));

        repoCompra.save(compra);
    }


    //TODO:bajar un servicio y que funcionen los demas

}
