package ar.utn.credicoop.msproductopersonalizado;

import ar.utn.credicoop.msproductopersonalizado.modelos.ProductoPersonalizado;
import ar.utn.credicoop.msproductopersonalizado.modelos.personalizacion.PosiblePersonalizacion;
import ar.utn.credicoop.msproductopersonalizado.repositorios.RepoProductoPersonalizado;
import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductoPersonalizadoController {

    @Autowired
    ProductoBaseProxy proxy;

    @Autowired
    private RepoProductoPersonalizado repoProductoPersonalizado;

    private Logger log = LoggerFactory.getLogger(ProductoPersonalizadoController.class);

    @Transactional
    @Retry(name = "default",fallbackMethod = "noDisponible")
    @RequestMapping(value = "/productosPersonalizados/", method = RequestMethod.POST,headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> crearProductoPersonalizado(@RequestBody ProductoPersonalizado productoPersonalizado) {
        //Comprobar existencia de producto base
        log.info("Se llamo al servidor ");
        if(proxy.existeProductoBase(productoPersonalizado.getProductoId())){

            boolean aceptaPosiblePersonalizacion= false;
            List<PosiblePersonalizacion> posibles = proxy.traerTodas(productoPersonalizado.getProductoId());
            PosiblePersonalizacion comparar = productoPersonalizado.getPersonalizacion().getPosiblePersonalizacion();
            for(PosiblePersonalizacion actual:posibles){
                if(actual.getArea().getNombreArea().equals(comparar.getArea().getNombreArea())&&actual.getTipo().getNombreTipo().equals(comparar.getTipo().getNombreTipo())){
                    aceptaPosiblePersonalizacion= true;
                }
            }

            if(aceptaPosiblePersonalizacion){
                //Hacer el post de producto personalizado
                Double precioPersonalizacion = productoPersonalizado.getPersonalizacion().getPrecioPersonalizacion();
                Double precioProductoBase = proxy.consultarProductoBase(productoPersonalizado.getProductoId()).getPrecioBase();

                productoPersonalizado.setPrecioFinal(precioProductoBase+precioPersonalizacion);

                repoProductoPersonalizado.save(productoPersonalizado);

                return ResponseEntity.ok(productoPersonalizado);
            }else{
                return new ResponseEntity<Object>("No acepta posible personalizacion", null, HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<Object>("No se encontro productoBase", null, HttpStatus.NOT_FOUND);
        }
    }

    public @ResponseBody ResponseEntity<Object> noDisponible(IllegalStateException ex){
        return new ResponseEntity<Object>("No se encontro Endpoint", null, HttpStatus.NOT_FOUND);
    }
    public @ResponseBody ResponseEntity<Object> noDisponible(FeignException ex){
        return new ResponseEntity<Object>("No se encontro Endpoint", null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/productosPersonalizados/{productoPersonalizadoId}")
    @ResponseStatus(HttpStatus.OK)
    Boolean existeProductoPersonalizado(@PathVariable("productoPersonalizadoId") Integer productoPersonalizadoId) {
        Optional<ProductoPersonalizado> productoPersonalizado = repoProductoPersonalizado.findById(productoPersonalizadoId);
        if(productoPersonalizado.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    @GetMapping("/productosPersonalizados/{productoPersonalizadoId}/datos")
    @ResponseStatus(HttpStatus.OK)
    RtaProductoPersonalizadoDTO consultarProductoPersonalizado(@PathVariable("productoPersonalizadoId") Integer productoPersonalizadoId){
        Optional<ProductoPersonalizado> productoPersonalizadoOptional= repoProductoPersonalizado.findById(productoPersonalizadoId);
        //TODO:Verficacion de exitencia de id.
        ProductoPersonalizado productoPersonalizado = productoPersonalizadoOptional.get();
        RtaProductoBaseDTO productoBaseDTO = proxy.consultarProductoBase(productoPersonalizado.getProductoId());

        return new RtaProductoPersonalizadoDTO(productoBaseDTO.getProductoBaseId(),productoBaseDTO.getNombre(),productoPersonalizado.getPrecioFinal());
    }

}
