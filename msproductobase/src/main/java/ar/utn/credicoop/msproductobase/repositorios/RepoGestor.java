package ar.utn.credicoop.msproductobase.repositorios;

import ar.utn.credicoop.msproductobase.modelos.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoGestor extends JpaRepository<Gestor,Integer> {
}
