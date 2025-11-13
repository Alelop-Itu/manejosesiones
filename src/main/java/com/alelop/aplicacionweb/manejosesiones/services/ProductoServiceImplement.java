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

public class ProductoServiceImplement {
    // Define la clase pública ProductoServiceImplement que implementa la interfaz ProductoService
    public class ProductoServiceImplement implements ProductoService {
        @Override
        public List<Producto> listar() {
            // Retorna una lista inmutable creada a partir de los elementos proporcionados
            return Arrays.asList(
                    new Producto(1L, "Laptop", "computación", 250.25),
                    new Producto(2L, "Refrigeradora", "cocina", 745.13),
                    new Producto(3L, "Cama", "dormitorio", 350.12));
        }
    }
}
