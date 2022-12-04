package ar.utn.credicoop.msventas.repositorios;

import ar.utn.credicoop.msventas.modelos.compra.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCompra extends JpaRepository<Compra,Integer> {
}
