package ar.utn.credicoop.msventas;

import ar.utn.credicoop.msventas.modelos.MediosDePago;
import ar.utn.credicoop.msventas.modelos.carrito.CarritoDeCompra;
import ar.utn.credicoop.msventas.modelos.compra.DatosPago;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraDTO {
    private Integer carritoDeCompraId;
    private DatosPago datosPago;
    private MediosDePago medioDePago;

    public CompraDTO() {
    }

    public CompraDTO(Integer carritoDeCompraId, DatosPago datosPago, MediosDePago medioDePago) {
        this.carritoDeCompraId = carritoDeCompraId;
        this.datosPago = datosPago;
        this.medioDePago = medioDePago;
    }
}
