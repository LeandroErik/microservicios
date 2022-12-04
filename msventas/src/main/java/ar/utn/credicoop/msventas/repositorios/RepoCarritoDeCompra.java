package ar.utn.credicoop.msventas.repositorios;

import ar.utn.credicoop.msventas.modelos.carrito.CarritoDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCarritoDeCompra extends JpaRepository<CarritoDeCompra,Integer> {
}
