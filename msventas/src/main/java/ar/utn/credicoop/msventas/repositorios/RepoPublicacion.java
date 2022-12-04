package ar.utn.credicoop.msventas.repositorios;

import ar.utn.credicoop.msventas.modelos.publicacion.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoPublicacion extends JpaRepository<Publicacion,Integer> {
}
