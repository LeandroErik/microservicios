package ar.utn.credicoop.msventas;

import ar.utn.credicoop.msventas.modelos.carrito.CarritoDeCompra;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraDTO {
    private Integer carritoDeCompraId;

    public CompraDTO() {
    }

    public CompraDTO(Integer carritoDeCompra) {
        this.carritoDeCompraId = carritoDeCompra;
    }
}
