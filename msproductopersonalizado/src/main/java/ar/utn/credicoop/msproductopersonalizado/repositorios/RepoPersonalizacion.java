package ar.utn.credicoop.msproductopersonalizado.repositorios;

import ar.utn.credicoop.msproductopersonalizado.modelos.personalizacion.Personalizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoPersonalizacion extends JpaRepository<Personalizacion,Integer> {
}
