package ar.utn.credicoop.msproductopersonalizado;

import ar.utn.credicoop.msproductopersonalizado.modelos.ProductoPersonalizado;
import ar.utn.credicoop.msproductopersonalizado.modelos.personalizacion.PosiblePersonalizacion;
import ar.utn.credicoop.msproductopersonalizado.repositorios.RepoProductoPersonalizado;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductoPersonalizadoController {

    @Autowired
    ProductoBaseProxy proxy;

    @Autowired
    private RepoProductoPersonalizado repoProductoPersonalizado;

    @RequestMapping(value = "/productosPersonalizados/", method = RequestMethod.POST,headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Object> crearProductoPersonalizado(@RequestBody ProductoPersonalizado productoPersonalizado) {
        //Comprobar existencia de producto base
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
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }
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
