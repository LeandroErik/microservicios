package ar.utn.credicoop.msproductopersonalizado.modelos.personalizacion;

import ar.utn.credicoop.msproductopersonalizado.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "personalizacion")
@Getter
@Setter
public class Personalizacion extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private Double precioPersonalizacion;

    @Column(name = "contenido")
    private String contenido;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "posible_personalizacion_id",referencedColumnName = "id")
    private PosiblePersonalizacion posiblePersonalizacion;


    public Personalizacion() {
    }

    public Personalizacion(String nombre) {
        this.nombre = nombre;
    }
}
