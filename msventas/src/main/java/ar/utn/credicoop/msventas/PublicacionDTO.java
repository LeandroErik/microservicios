package ar.utn.credicoop.msventas;

import ar.utn.credicoop.msventas.modelos.Vendedor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PublicacionDTO {
    private Vendedor vendedor;
    private Integer productoPersonalizadoId;

    public PublicacionDTO() {
    }

    public PublicacionDTO(Vendedor vendedor, Integer productoPersonalizadoId) {
        this.vendedor = vendedor;
        this.productoPersonalizadoId = productoPersonalizadoId;
    }
}
