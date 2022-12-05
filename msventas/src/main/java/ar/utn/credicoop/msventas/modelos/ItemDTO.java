package ar.utn.credicoop.msventas.modelos;

import ar.utn.credicoop.msventas.PublicacionDTO;
import ar.utn.credicoop.msventas.modelos.publicacion.Publicacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

    private PublicacionDTO publicacion;
    private Integer cantidad;

    public ItemDTO() {
    }

    public ItemDTO(PublicacionDTO publicacion, Integer cantidad) {
        this.publicacion = publicacion;
        this.cantidad = cantidad;
    }
}
