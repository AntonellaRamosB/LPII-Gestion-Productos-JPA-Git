package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_categorias")
@Getter
@Setter
public class Categoria {

    @Id
    private int idcategoria;

    private String descripcion;

    @Override
    public String toString() {
        return idcategoria + " - " + descripcion;
    }
}