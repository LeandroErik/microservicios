package ar.utn.credicoop.msventas.modelos.compra;

import ar.utn.credicoop.msventas.modelos.Persistente;
import ar.utn.credicoop.msventas.modelos.carrito.CarritoDeCompra;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compra")
@Getter
@Setter
public class Compra extends Persistente {

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "carrito_de_compra_id", referencedColumnName = "id")
    private CarritoDeCompra carritoDeCompra;

    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;

    @Column(name = "hora", columnDefinition = "TIME")
    private LocalTime hora;

    @OneToMany(mappedBy = "compra",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<EstadoCompra> estados;

    public Compra() {
        this.estados = new ArrayList<>();
    }

    public void agregarEstado(EstadoCompra estado){
        estado.setCompra(this);
        estados.add(estado);
    }


}

// TODO agregar precio total en la compra
// TODO agregar datos para el pago
// que podriamos en los datos para el pago que tiene la compra,ya que depende de el medio de pago,estaria bien que solo guarde un string.