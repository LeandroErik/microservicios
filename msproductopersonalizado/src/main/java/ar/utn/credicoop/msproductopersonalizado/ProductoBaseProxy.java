package ar.utn.credicoop.msproductopersonalizado;

import ar.utn.credicoop.msproductopersonalizado.modelos.personalizacion.PosiblePersonalizacion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "productobase")
public interface ProductoBaseProxy {
    //la firma de otro microservicio que quieras consumir.

    @GetMapping("/productos/{productoBaseId}")
    Boolean existeProductoBase(@PathVariable("productoBaseId") Integer productoBaseId);

    @GetMapping("/productos/{productoBaseId}/posiblesPersonalizaciones")
    List<PosiblePersonalizacion> traerTodas(@PathVariable("productoBaseId") Integer productoBaseId);

    @GetMapping("/productos/{productoBaseId}/datos")
    RtaProductoBaseDTO consultarProductoBase(@PathVariable("productoBaseId") Integer productoBaseId);


    //@GetMapping("/productos/{productoBaseId}/admitePersonalizacion/") Este Get lo toma como POST(Puede ser que el GET no soporte @requestBody)
    @RequestMapping(value = "/productos/{productoBaseId}/admitePersonalizacion/", method = RequestMethod.GET)
    Boolean admitePosiblePersonalizacion(@PathVariable("productoBaseId") Integer productoBaseId, @RequestBody PosiblePersonalizacion posiblePersonalizacion);


}


