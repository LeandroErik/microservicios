package ar.utn.credicoop.msproductopersonalizado;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productobase")
public interface ProductoBaseProxy {
    //la firma de otro microservicio que quieras consumir.

    @GetMapping("/productos/{productoBaseId}")
    RtaProductoBaseDTO existeProductoBase(@PathVariable("productoBaseId") Integer productoBaseId);

}
