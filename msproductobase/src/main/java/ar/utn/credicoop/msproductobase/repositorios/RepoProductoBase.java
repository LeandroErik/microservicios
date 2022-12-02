package ar.utn.credicoop.msproductobase.repositorios;

import ar.utn.credicoop.msproductobase.modelos.ProductoBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoProductoBase extends JpaRepository<ProductoBase,Integer> {
}
