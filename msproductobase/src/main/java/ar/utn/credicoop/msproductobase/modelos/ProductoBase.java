package ar.utn.credicoop.msproductobase.modelos;

import ar.utn.credicoop.msproductobase.modelos.personalizacion.PosiblePersonalizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="producto_base")
@Getter
@Setter
public class ProductoBase extends Persistente{

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "producto_base_id", referencedColumnName = "id")
    private List<PosiblePersonalizacion> personalizacionesPermitidas;

    @Column(name="tiempo_fabricacion")
    private Integer tiempoFabricacion;

    @Column(name = "precio_base")
    private Double precioBase;


    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="gestor_id",referencedColumnName = "id")
    private Gestor gestor;

    public ProductoBase(){}

    public ProductoBase(String nombre){
        this.nombre = nombre;
        this.personalizacionesPermitidas = new ArrayList<>();
    }

    public void agregarPersonalizacion(PosiblePersonalizacion posible){
        personalizacionesPermitidas.add(posible);
    }

}
