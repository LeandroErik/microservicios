package ar.utn.credicoop.msproductobase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RtaProductoBaseDTO {
    private String nombre;
    private Double precio;

    public RtaProductoBaseDTO() {
    }

    public RtaProductoBaseDTO(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
