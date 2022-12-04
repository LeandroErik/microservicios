package ar.utn.credicoop.msventas.modelos.compra;

import ar.utn.credicoop.msventas.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "compra_estado")
@Getter
@Setter
public class EstadoCompra extends Persistente {

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private PosibleEstadoCompra estadoReal;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "compra_id", referencedColumnName = "id")
    private Compra compra;

    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;

    @Column(name = "hora", columnDefinition = "TIME")
    private LocalTime hora;

    public EstadoCompra() {
    }

    public EstadoCompra(PosibleEstadoCompra estadoReal, LocalDate fecha, LocalTime hora) {
        this.estadoReal = estadoReal;
        this.fecha = fecha;
        this.hora = hora;
    }
}
