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
            //Comprobar que admita la personbalizacion
            boolean esPosiblePersonalizacion= false;
            List<PosiblePersonalizacion> posibles = proxy.traerTodas(productoPersonalizado.getProductoId());
            PosiblePersonalizacion comparar = productoPersonalizado.getPersonalizacion().getPosiblePersonalizacion();
            for(PosiblePersonalizacion actual:posibles){
                if(actual.getArea().getNombreArea().equals(comparar.getArea().getNombreArea())&&actual.getTipo().getNombreTipo().equals(comparar.getTipo().getNombreTipo())){
                    esPosiblePersonalizacion= true;
                }
            }
            if(esPosiblePersonalizacion){
                //Hacer el post de producto personalizado
                repoProductoPersonalizado.save(productoPersonalizado);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/productosPersonalizados/{productoPersonalizadoId}")
    Boolean existeProductoPersonalizado(@PathVariable("productoPersonalizadoId") Integer productoPersonalizadoId) {
        Optional<ProductoPersonalizado> productoPersonalizado = repoProductoPersonalizado.findById(productoPersonalizadoId);
        if(productoPersonalizado.isPresent()){
            return true;
        }else{
            return false;
        }
    }

}
