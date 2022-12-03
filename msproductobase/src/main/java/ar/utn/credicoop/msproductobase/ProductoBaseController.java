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
    public  @ResponseBody ResponseEntity<Object> existeProductoBase(@PathVariable("productoBaseId") Integer productoBaseId){
        Optional<ProductoBase> productoBase = repoProducto.findById(productoBaseId);
        if(productoBase.isPresent()){
            return ResponseEntity.ok(productoBase.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/productos/")
    public List<ProductoBase> obtenerProductosBase(){
        return repoProducto.findAll();
    }


    @PostMapping("/productos/")
    @ResponseStatus(HttpStatus.OK)
    public void crearProductoBase(@RequestBody ProductoBase productoBase){
        repoProducto.save(productoBase);
    }

    @Transactional
    @PostMapping("/productos/{productoBaseId}/posiblepersonalizacion")
    @ResponseStatus(HttpStatus.OK)
    public void agregarPosiblepersonalizacion(@PathVariable("productoBaseId") Integer productoBaseId, @RequestBody PosiblePersonalizacion posiblePersonalizacion){
        Optional<ProductoBase> productoOpcional =repoProducto.findById(productoBaseId);
        //Corroborar que el optional de ok
        ProductoBase producto = productoOpcional.get();
        producto.agregarPersonalizacion(posiblePersonalizacion);

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
