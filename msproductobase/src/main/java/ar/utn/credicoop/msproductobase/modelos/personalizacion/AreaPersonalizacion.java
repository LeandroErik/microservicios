package ar.utn.credicoop.msproductobase.modelos.personalizacion;

import ar.utn.credicoop.msproductobase.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "area_personalizacion")
@Getter
@Setter
public class AreaPersonalizacion extends Persistente {

    @Column(name="nombre_area")
    private String nombreArea;

    public AreaPersonalizacion() {
    }

    public AreaPersonalizacion(String nombreArea) {
        this.nombreArea = nombreArea;
    }

}
