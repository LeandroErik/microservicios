package ar.utn.credicoop.msventas;

public class CarritoDeCompraDTO {
    private Integer carritoDeCompraId;
    private Double precioTotalCarrito;

    public CarritoDeCompraDTO() {
    }

    public CarritoDeCompraDTO(Integer carritoDeCompraId, Double precioTotalCarrito) {
        this.carritoDeCompraId = carritoDeCompraId;
        this.precioTotalCarrito = precioTotalCarrito;
    }
}
