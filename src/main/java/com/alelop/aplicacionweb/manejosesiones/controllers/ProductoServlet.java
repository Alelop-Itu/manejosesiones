package com.alelop.aplicacionweb.manejosesiones.controllers;
/*
 * Autor: Alejandra López
 * Fecha: 11/11/2025
 * Descripción: Esta clase llamada ProductoServlet,
 * se encarha de modelar el servlet de Producto, clase hija de HttpServlet
 *
 */
// Importa la clase ServletException del API de Jakarta Servlet


import com.alelop.aplicacionweb.manejosesiones.models.Producto;
import com.alelop.aplicacionweb.manejosesiones.services.LoginService;
import com.alelop.aplicacionweb.manejosesiones.services.LoginServiceSessionImplement;
import com.alelop.aplicacionweb.manejosesiones.services.ProductoService;
import com.alelop.aplicacionweb.manejosesiones.services.ProductoServiceImplement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


// Clave para acceso desde index
@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Instancia del servicio de productos

        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional = auth.getUsername(req);

        // --- LÓGICA DEL CONTADOR DE HITS DE SESIÓN (MOVIDA AQUÍ) ---
        Integer counter = null;
        if(usernameOptional.isPresent()) {
            HttpSession session = req.getSession();
            // Obtener el contador de la sesión (puede ser null la primera vez)
            counter = (Integer) session.getAttribute("hitCounter");

            if (counter == null) {
                // Si es la primera vez, inicializamos en 1
                counter = 1;
            } else {
                // Si ya existe, incrementamos
                counter++;
            }
            // Guardamos el nuevo valor en la sesión
            session.setAttribute("hitCounter", counter);
        }
        // -----------------------------------------------------------

        // Establece tipo de contenido HTML con codificación UTF-8
        resp.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {

            // Comienza a generar la respuesta HTML con Bootstrap
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
            out.println("<title>Lista de Productos</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head>");
            out.println("<body class='bg-light'>");

            // Contenedor principal
            out.println("<div class='container mt-5'>");
            out.println("<div class='card shadow-lg'>");
            out.println("<div class='card-header bg-success text-white text-center'>");
            out.println("<h1 class='mb-0'>Lista de Productos</h1>");
            out.println("</div>");
            out.println("<div class='card-body'>");

            // Mensaje de Bienvenida y Contador
            if(usernameOptional.isPresent()) {
                out.println("<div class='alert alert-success' role='alert'>");
                out.println("Hola <strong>" + usernameOptional.get() + "</strong>. ¡Bienvenido!");
                out.println("</div>");

                // --- MOSTRAR EL CONTADOR ---
                out.println("<p class='text-muted mt-3 text-center'>Esta página se ha recargado <strong>" + counter + "</strong> veces en esta sesión.</p>");
                // ---------------------------
            } else {
                out.println("<div class='alert alert-warning' role='alert'>");
                out.println("Inicia sesión para ver los precios de los productos.");
                out.println("</div>");
            }

            // Tabla de productos
            out.println("<div class='table-responsive'>");
            out.println("<table class='table table-bordered table-hover align-middle'>");
            out.println("<thead class='table-success text-center'>");
            out.println("<tr>");
            out.println("<th>ID PRODUCTO</th>");
            out.println("<th>NOMBRE</th>");
            out.println("<th>CATEGORÍA</th>");
            if (usernameOptional.isPresent()) {
                out.println("<th>PRECIO</th>");
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            productos.forEach(p->{
                out.println("<tr>");
                out.println("<td class='text-center'>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getCategoria() + "</td>");
                if (usernameOptional.isPresent()) {
                    out.println("<td class='text-end'>$ " + String.format("%.2f", p.getPrecio()) + "</td>");
                }
                out.println("</tr>");
            });
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");

            // Botones de sesión
            out.println("<div class='mt-4 text-center'>");

            if (usernameOptional.isPresent()) {
                // Formulario POST para cerrar sesión
                out.println("<form action='" + req.getContextPath() + "/logout' method='post' style='display:inline;'>");
                out.println("<button type='submit' class='btn btn-danger me-2'>Cerrar sesión</button>");
                out.println("</form>");
            } else {
                // Enlace para iniciar sesión
                out.println("<a href='" + req.getContextPath() + "/login.jsp' class='btn btn-primary me-2'>Iniciar sesión</a>");
            }

            // Botón Regresar (redirige correctamente al inicio)
            out.println("<a href='" + req.getContextPath() + "/index.html' class='btn btn-outline-secondary'>Regresar al inicio</a>");
            out.println("</div>");

            // Cierre de card y container
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            // Script de Bootstrap
            out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}

