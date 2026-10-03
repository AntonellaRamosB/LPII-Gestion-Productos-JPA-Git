package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_proveedor")
@Getter
@Setter
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproveedor;

    private String nombre_rs;
    private String telefono;
    private String email;

    @Override
    public String toString() {
        return idproveedor + " - " + nombre_rs;
    }
}