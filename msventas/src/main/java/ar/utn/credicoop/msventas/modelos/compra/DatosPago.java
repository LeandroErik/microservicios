package ar.utn.credicoop.msventas.modelos.compra;

import ar.utn.credicoop.msventas.modelos.Persistente;
import ar.utn.credicoop.msventas.modelos.TipoDocumento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Entity
public class DatosPago extends Persistente {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoDeDocumento")
    private TipoDocumento tipoDocumento;
    @Column
    private String documento;
    @Column
    private String nombre;

    public DatosPago() {
    }

    public DatosPago(TipoDocumento tipoDocumento, String documento, String nombre) {
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nombre = nombre;
    }
}
