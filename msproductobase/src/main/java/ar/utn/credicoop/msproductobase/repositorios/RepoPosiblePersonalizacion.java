package ar.utn.credicoop.msproductobase.repositorios;

import ar.utn.credicoop.msproductobase.modelos.personalizacion.PosiblePersonalizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoPosiblePersonalizacion extends JpaRepository<PosiblePersonalizacion,Integer> {
}
