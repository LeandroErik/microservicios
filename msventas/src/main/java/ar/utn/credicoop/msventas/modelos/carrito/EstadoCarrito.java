package ar.utn.credicoop.msventas.modelos.carrito;

import ar.utn.credicoop.msventas.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "carrito_estado")
@Getter
@Setter
public class EstadoCarrito extends Persistente {

        @Enumerated(EnumType.STRING)
        @Column(name = "estado")
        private PosibleEstadoCarrito estadoReal;

        @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
        @JoinColumn(name = "carrito_de_compra_id", referencedColumnName = "id")
        private CarritoDeCompra carritoDeCompra;

        @Column(name = "fecha", columnDefinition = "DATE")
        private LocalDate fecha;

        @Column(name = "hora", columnDefinition = "TIME")
        private LocalTime hora;

        public EstadoCarrito() {
        }

        public EstadoCarrito(PosibleEstadoCarrito estadoReal, LocalDate fecha, LocalTime hora) {
                this.estadoReal = estadoReal;
                this.fecha = fecha;
                this.hora = hora;
        }
}
