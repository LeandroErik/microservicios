package ar.utn.credicoop.msproductopersonalizado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoPersonalizadoController {

    @Autowired
    ProductoBaseProxy proxy;

    @GetMapping("/productoPers/{productoPersId}")
    public  String crearProductoPersonalizado(@PathVariable("productoPersId") Integer productoPersId){

        RtaProductoBaseDTO respuesta = proxy.existeProductoBase(productoPersId);

        return "Se creo producto personalizado: "+ respuesta.getNombre() +" "+ respuesta.getPrecio();
    }

}
