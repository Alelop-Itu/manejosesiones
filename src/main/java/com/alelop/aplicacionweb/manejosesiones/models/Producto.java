package com.alelop.aplicacionweb.manejosesiones.models;
/*
 * Autor: Alejandra López
 * Fecha: 11/11/2025
 * Descripción: Esta clase denominada Producto, se encarga de modelar el producto que tiene sus atributos:
 * idProducto, nombre, categoria, precio ; con su contructor sin parámetros y con parámetros
 * sus métodos Getter y Setter
 */

public class Producto {
    // Declaramos la variable para el ID del producto (tipo Long)
    private Long idProducto;
    // Declaramos la variable para el nombre del producto (tipo String)
    private String nombre;
    // Declaramos la variable para la categoría del producto (tipo String)
    private String categoria;
    // Declaramos la variable para el precio del producto (tipo Double)
    private Double precio;

    // Constructor vacío de la clase Producto

    public Producto(){}

    /* Constructor que inicializa todos los campos de la clase Producto
     * @param idProducto, nombre ,categoria ,precio
     */

    public Producto(Long idProducto, String nombre, String categoria, Double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    //Métodos Get and set
    public Long getIdProducto() {
        // Retorna el valor de la variable 'idProducto'
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        // Asigna el valor del parámetro 'idProducto' a la variable de instancia 'this.idProducto'
        this.idProducto = idProducto;
    }

    public String getNombre() {
        // Retorna el valor de la variable 'nombre'
        return nombre;
    }


    public void setNombre(String nombre) {
        // Asigna el valor del parámetro 'nombre' a la variable de instancia 'this.nombre'
        this.nombre = nombre;
    }

    public String getCategoria() {
        // Retorna el valor de la variable 'categoria'
        return categoria;
    }

    public void setCategoria(String categoria) {
        // Asigna el valor del parámetro 'categoria' a la variable de instancia 'this.categoria'
        this.categoria = categoria;
    }

    public Double getPrecio() {
        // Retorna el valor de la variable 'precio'
        return precio;
    }

    public void setPrecio(Double precio) {
        // Asigna el valor del parámetro 'precio' a la variable de instancia 'this.precio'
        this.precio = precio;
    }

}
