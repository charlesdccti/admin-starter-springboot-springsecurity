package com.github.adminfaces.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Charles Ferreira
 *
 */

@Entity
public class Bike {

    @Id
    @GeneratedValue
    private Integer id;
    private String marca;
    private String tipo;
    private Date dataCompra;
    private Date dataEntrega;

    @ManyToOne
    private User user;


    public Bike() {

    }

    public Bike(String marca, String tipo, Date dataCompra, Date dataEntrega) {
        this.marca = marca;
        this.tipo = tipo;
        this.dataCompra = dataCompra;
        this.dataEntrega = dataEntrega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Objects.equals(id, bike.id) &&
                Objects.equals(marca, bike.marca) &&
                Objects.equals(tipo, bike.tipo) &&
                Objects.equals(dataCompra, bike.dataCompra) &&
                Objects.equals(dataEntrega, bike.dataEntrega);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, marca, tipo, dataCompra, dataEntrega);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
