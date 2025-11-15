package com.alelop.aplicacionweb.manejosesiones.services;
/*
 * Autor: Alejandra López
 * Fecha: 11/11/2025
 * Descripción: Esta clase denominada ProductoServiceImplement, se encarga de implementar
 * la interfaz de ProductoService
 */

import com.alelop.aplicacionweb.manejosesiones.models.Producto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Define la clase pública ProductoServiceImplement que implementa la interfaz ProductoService
public class ProductoServiceImplement implements ProductoService {
    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L,"Laptop","Computación",250.25),
                new Producto(2L,"Refrigeradora","Cocina",745.13),
                new Producto(3L,"Cama","Dormitorio",350.12));
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getIdProducto().equals(id)).findAny();
    }
}