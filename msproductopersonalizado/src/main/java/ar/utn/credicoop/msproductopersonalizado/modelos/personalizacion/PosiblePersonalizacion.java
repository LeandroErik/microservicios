package ar.utn.credicoop.msproductopersonalizado.modelos.personalizacion;

import ar.utn.credicoop.msproductopersonalizado.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "posible_personalizacion")
@Getter
@Setter
public class PosiblePersonalizacion extends Persistente {

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "area_id",referencedColumnName = "id")
    private AreaPersonalizacion area;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "tipo_id",referencedColumnName = "id")
    private TipoPersonalizacion tipo;

    public PosiblePersonalizacion() {
    }

    public PosiblePersonalizacion(AreaPersonalizacion area, TipoPersonalizacion tipo) {
        this.area = area;
        this.tipo = tipo;
    }
}
