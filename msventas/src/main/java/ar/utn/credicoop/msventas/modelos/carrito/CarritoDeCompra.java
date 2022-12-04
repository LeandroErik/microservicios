package ar.utn.credicoop.msventas.modelos.carrito;

import ar.utn.credicoop.msventas.modelos.Comprador;
import ar.utn.credicoop.msventas.modelos.Item;
import ar.utn.credicoop.msventas.modelos.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito_de_compra")
@Getter
@Setter
public class CarritoDeCompra extends Persistente {

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "comprador_id", referencedColumnName = "id")
    private Comprador comprador;

    @OneToMany(mappedBy = "carritoDeCompra",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Item> items;

    @OneToMany(mappedBy = "carritoDeCompra",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<EstadoCarrito> estados;

    public CarritoDeCompra() {
        this.estados = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public void agregarItem(Item item){
        items.add(item);
        item.setCarritoDeCompra(this);
    }

    public void agregarEstado(EstadoCarrito estado){
        estado.setCarritoDeCompra(this);
        estados.add(estado);
    }
}