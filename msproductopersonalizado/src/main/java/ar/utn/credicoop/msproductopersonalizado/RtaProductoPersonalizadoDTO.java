package ar.utn.credicoop.msproductopersonalizado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RtaProductoPersonalizadoDTO {
    private Integer productoBaseId;
    private String nombre;
    private Double precioBase;
    private Double precioFinal;

    public RtaProductoPersonalizadoDTO() {
    }

    public RtaProductoPersonalizadoDTO(Integer productoBaseId, String nombre, Double precioBase, Double precioFinal) {
        this.productoBaseId = productoBaseId;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.precioFinal = precioFinal;
    }

    public RtaProductoPersonalizadoDTO(RtaProductoBaseDTO productoBase, Double precioFinal) {
        this.productoBaseId = productoBase.getProductoBaseId();
        this.nombre = productoBase.getNombre();
        this.precioBase = productoBase.getPrecioBase();
        this.precioFinal = precioFinal;
    }
}
