package ar.utn.credicoop.msproductopersonalizado.repositorios;

import ar.utn.credicoop.msproductopersonalizado.modelos.ProductoPersonalizado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoProductoPersonalizado extends JpaRepository<ProductoPersonalizado,Integer> {
}
