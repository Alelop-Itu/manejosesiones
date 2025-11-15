package com.alelop.aplicacionweb.manejosesiones.services;
/*
 * Autor: Alejandra López
 * Fecha: 11/11/2025
 * Descripción: Esta clase llamada ProductoService es una clase abstracta que sirve como
 * plantilla que va a utilizarse "Producto" para el uso en el Servlet
 */

import com.alelop.aplicacionweb.manejosesiones.models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    // Declara el método 'listar' que debe ser implementado
    // Este métodos no recibe argumentos y retorna una lista de objetos Producto
    List<Producto> listar();
    Optional<Producto> porId(Long id);
}