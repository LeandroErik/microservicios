package ar.utn.credicoop.msventas.modelos;

import ar.utn.credicoop.msventas.modelos.publicacion.Publicacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

    private Publicacion publicacion;
    private Integer cantidad;

    public ItemDTO() {
    }

    public ItemDTO(Publicacion publicacion, Integer cantidad) {
        this.publicacion = publicacion;
        this.cantidad = cantidad;
    }
}
