package ar.utn.credicoop.msventas.modelos.publicacion;

import ar.utn.credicoop.msventas.modelos.Persistente;
import ar.utn.credicoop.msventas.modelos.Vendedor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacion")
@Getter
@Setter
public class Publicacion extends Persistente {

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Vendedor vendedor;

    @Column
    private Integer productoPersonalizadoId;

    @Column(name = "fechaDePublicacion", columnDefinition = "DATE")
    private LocalDate fechaDePublicacion;

    @OneToMany(mappedBy = "publicacion",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<EstadoPublicacion> estados;

    public Publicacion() {
        this.estados = new ArrayList<>();
    }

    public void agregarEstados(EstadoPublicacion estado) {
        estado.setPublicacion(this);
        this.estados.add(estado);
    }
}