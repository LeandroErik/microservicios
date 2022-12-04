package ar.utn.credicoop.msproductopersonalizado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RtaProductoPersonalizadoDTO {
    private Integer productoBaseId;
    private String nombre;
    private Double precioFinal;

    public RtaProductoPersonalizadoDTO() {
    }

    public RtaProductoPersonalizadoDTO(Integer productoBaseId, String nombre, Double precioFinal) {
        this.productoBaseId = productoBaseId;
        this.nombre = nombre;
        this.precioFinal = precioFinal;
    }
}
