package ar.utn.credicoop.msproductopersonalizado.modelos;

import ar.utn.credicoop.msproductopersonalizado.modelos.personalizacion.Personalizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "producto_personalizado")
@Getter
@Setter
public class ProductoPersonalizado extends Persistente{

    @Column
    private Integer productoId;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "personalizacion_id",referencedColumnName = "id")
    private Personalizacion personalizacion;

    @Column(name = "precio_final")
    private Double precioFinal;

    public ProductoPersonalizado() {

    }

    public ProductoPersonalizado(Integer productoId, Personalizacion personalizacion, Float precioFinal) {
        this.productoId = productoId;
    }
}