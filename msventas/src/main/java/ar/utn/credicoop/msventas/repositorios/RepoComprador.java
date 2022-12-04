package ar.utn.credicoop.msventas.repositorios;

import ar.utn.credicoop.msventas.modelos.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoComprador extends JpaRepository<Comprador,Integer> {
}
