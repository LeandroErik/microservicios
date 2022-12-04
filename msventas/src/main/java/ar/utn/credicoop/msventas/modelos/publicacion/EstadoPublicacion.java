package ar.utn.credicoop.msventas.modelos.publicacion;

import ar.utn.credicoop.msventas.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "publicacion_estado")
@Getter
@Setter
public class EstadoPublicacion extends Persistente {

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private PosibleEstadoPublicacion estadoReal;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "publicacion_id", referencedColumnName = "id")
    private Publicacion publicacion;

    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;

    @Column(name = "hora", columnDefinition = "TIME")
    private LocalTime hora;

    public EstadoPublicacion() {
    }

    public EstadoPublicacion(PosibleEstadoPublicacion estadoReal, LocalDate fecha, LocalTime hora) {
        this.estadoReal = estadoReal;
        this.fecha = fecha;
        this.hora = hora;
    }
}
