package ar.utn.credicoop.msventas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "productopersonalizado")
public interface ProductoPersonalizadoProxy {

    @GetMapping("/productosPersonalizados/{productoPersonalizadoId}")
    Boolean existeProductoPersonalizado(@PathVariable("productoPersonalizadoId") Integer productoPersonalizadoId);

    @GetMapping("/productosPersonalizados/{productoPersonalizadoId}/datos")
    RtaProductoPersonalizadoDTO consultarProductoPersonalizado(@PathVariable("productoPersonalizadoId") Integer productoPersonalizadoId);

    }


