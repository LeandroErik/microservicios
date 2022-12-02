package ar.utn.credicoop.msproductobase.modelos.personalizacion;

import ar.utn.credicoop.msproductobase.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_personalizacion")
@Getter
@Setter
public class TipoPersonalizacion extends Persistente {

    public TipoPersonalizacion() {
    }

    public TipoPersonalizacion(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    @Column(name = "nombre_tipo")
    private String nombreTipo;

}
