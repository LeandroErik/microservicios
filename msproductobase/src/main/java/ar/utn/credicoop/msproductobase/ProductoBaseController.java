package ar.utn.credicoop.msproductobase;

import ar.utn.credicoop.msproductobase.modelos.ProductoBase;
import ar.utn.credicoop.msproductobase.modelos.personalizacion.PosiblePersonalizacion;
import ar.utn.credicoop.msproductobase.repositorios.RepoPosiblePersonalizacion;
import ar.utn.credicoop.msproductobase.repositorios.RepoProductoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductoBaseController{

    @Value("${server.port}")
    private String puerto;

    @Autowired
    private RepoProductoBase repoProducto;
    @Autowired
    private RepoPosiblePersonalizacion repoPosible;

    @GetMapping("/productos/{productoBaseId}")
    public  Boolean existeProductoBase(@PathVariable("productoBaseId") Integer productoBaseId){
        Optional<ProductoBase> productoBase = repoProducto.findById(productoBaseId);
        if(productoBase.isPresent()){
            return true;
        }else{
            return false;
        }
    }
    @GetMapping("/productos/{productoBaseId}/posiblesPersonalizaciones")
    public List<PosiblePersonalizacion> traerTodas(@PathVariable("productoBaseId") Integer productoBaseId){
        return repoProducto.findById(productoBaseId).get().getPersonalizacionesPermitidas();
    }

    //@GetMapping("/productos/{productoBaseId}/admitePersonalizacion/")
    @RequestMapping(value = "/productos/{productoBaseId}/admitePersonalizacion/", method = RequestMethod.GET)
    public Boolean admitePosiblePersonalizacion(@PathVariable("productoBaseId") Integer productoBaseId, @RequestBody PosiblePersonalizacion posiblePersonalizacion){
          Optional<ProductoBase> productoBaseOpcional = repoProducto.findById(productoBaseId);
          ProductoBase productoBase = productoBaseOpcional.get();
          Boolean esPosiblePersonalizacion = false;

          for(PosiblePersonalizacion actual:productoBase.getPersonalizacionesPermitidas()){
              if(actual.getArea().getNombreArea().equals(posiblePersonalizacion.getArea().getNombreArea())&&actual.getTipo().getNombreTipo().equals(posiblePersonalizacion.getTipo().getNombreTipo())){
                  esPosiblePersonalizacion=true;
              }
          }
        return esPosiblePersonalizacion;
    }

    @GetMapping("/productos/")
    public List<ProductoBase> obtenerProductosBase(){
        return repoProducto.findAll();
    }

    @GetMapping("/productos/{productoBaseId}/datos")
    public RtaProductoBaseDTO consultarProductoBase(@PathVariable("productoBaseId") Integer productoBaseId){
        //TODO:Verficicar que exista ese producto base
        ProductoBase productoBase = repoProducto.findById(productoBaseId).get();

        return new RtaProductoBaseDTO(productoBase.getId(),productoBase.getNombre(), productoBase.getPrecioBase());
    }


    @PostMapping("/productos/")
    @ResponseStatus(HttpStatus.OK)
    public void crearProductoBase(@RequestBody ProductoBase productoBase){
        repoProducto.save(productoBase);
    }

    @Transactional
    @PostMapping("/productos/{productoBaseId}/posiblepersonalizacion")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> agregarPosiblepersonalizacion(@PathVariable("productoBaseId") Integer productoBaseId, @RequestBody PosiblePersonalizacion posiblePersonalizacion){
        Optional<ProductoBase> productoOpcional =repoProducto.findById(productoBaseId);
        if(productoOpcional.isPresent()){
            ProductoBase producto = productoOpcional.get();
            producto.agregarPersonalizacion(posiblePersonalizacion);
            return ResponseEntity.ok(producto);
        }else{

            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping("/posiblepersonalizacion/")
    public void crearPosiblePersonalizacion(@RequestBody PosiblePersonalizacion pos){

        PosiblePersonalizacion posible = new PosiblePersonalizacion();
        posible.setArea(pos.getArea());
        posible.setTipo(pos.getTipo());
        repoPosible.save(posible);
    }

}
