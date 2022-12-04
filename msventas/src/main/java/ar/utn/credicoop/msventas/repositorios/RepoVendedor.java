package ar.utn.credicoop.msventas.repositorios;

import ar.utn.credicoop.msventas.modelos.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoVendedor extends JpaRepository<Vendedor,Integer> {
}
