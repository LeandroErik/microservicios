package ar.utn.credicoop.msproductopersonalizado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RtaProductoBaseDTO {
    private Integer productoBaseId;
    private String nombre;
    private Double precioBase;

    public RtaProductoBaseDTO() {
    }

    public RtaProductoBaseDTO(Integer productoBaseId, String nombre, Double precio) {
        this.productoBaseId = productoBaseId;
        this.nombre = nombre;
        this.precioBase = precio;
    }
}
