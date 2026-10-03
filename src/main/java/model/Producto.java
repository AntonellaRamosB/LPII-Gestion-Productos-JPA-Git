package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_productos")
@Getter
@Setter
public class Producto {

    @Id
    private String id_prod;

    private String des_prod;
    private int stk_prod;
    private double pre_prod;
    private boolean est_prod;

    @ManyToOne
    @JoinColumn(name = "idcategoria")
    private Categoria objCategoria;

    @ManyToOne
    @JoinColumn(name = "idproveedor")
    private Proveedor objProveedor;
}