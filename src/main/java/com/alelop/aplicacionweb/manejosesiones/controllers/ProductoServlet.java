package com.alelop.aplicacionweb.manejosesiones.controllers;
/*
 * Autor: Alejandra López
 * Fecha: 11/11/2025
 * Descripción: Esta clase llamada ProductoServlet,
 * se encarga de modelar el servlet de Producto, clase hija de HttpServlet
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

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional = auth.getUsername(req);

        // Se utiliza este método para obtener el contador de ingresos de la sesión
        HttpSession session = req.getSession(false);
        Integer contadorIngresos = (session != null) ? (Integer) session.getAttribute("contadorIngresos") : null;


        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Listado de Productos</title>");
            // Se utiliza este método para enlazar la librería Bootstrap a través de un CDN
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head>");
            // Esta clase se encarga de dar un fondo verde muy claro (Floral) a toda la página
            out.println("<body class='bg-success-subtle'>");

            // Esta clase se encarga de crear un contenedor centrado con margen superior
            out.println("<div class='container mt-5'>");
            // Esta clase se encarga de crear una tarjeta con sombra y borde verde (Floral)
            out.println("<div class='card shadow-lg border-success'>");
            out.println("<div class='card-body'>");

            // Esta clase se encarga de usar el color verde de éxito (success) y centrar el título
            out.println("<h1 class='text-center mb-4 text-success'>Listado de productos</h1>");

            // Mostrar mensaje de bienvenida si hay sesión activa
            if (usernameOptional.isPresent()) {
                // Esta clase se encarga de mostrar un mensaje de éxito con fondo verde
                out.println("<div class='alert alert-success text-center' role='alert'>");
                out.println("Hola <strong>" + usernameOptional.get() + "</strong>, ¡bienvenido!");

                // Se utiliza este método para mostrar el contador de ingresos
                if (contadorIngresos != null && contadorIngresos > 0) {
                    out.println("<p class='mb-0'><small>Esta es tu visita número: <strong>" + contadorIngresos + "</strong></small></p>");
                }
                out.println("</div>");
            } else {
                // Esta clase se encarga de mostrar un mensaje de advertencia
                out.println("<div class='alert alert-warning text-center' role='alert'>");
                out.println("Inicia sesión para ver los precios y agregar productos al carro.");
                out.println("</div>");
            }

            // Tabla de productos
            // Estas clases se encargan de aplicar un estilo moderno de tabla (bandas, bordes, centrado)
            out.println("<table class='table table-striped table-bordered text-center align-middle mt-3'>");
            // Esta clase se encarga de aplicar un fondo verde (success) a la cabecera de la tabla
            out.println("<thead class='table-success'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Categoría</th>");
            if (usernameOptional.isPresent()) {
                out.println("<th>Precio</th>");
                out.println("<th>Opciones</th>");
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            for (Producto p : productos) {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getCategoria() + "</td>");
                if (usernameOptional.isPresent()) {
                    out.println("<td>$" + p.getPrecio() + "</td>");
                    // Estas clases se encargan de estilizar el enlace como un botón pequeño y verde (success)
                    out.println("<td><a href=\""
                            +req.getContextPath()
                            +"/agregar-carro?id="
                            +p.getIdProducto()
                            +"\" class='btn btn-sm btn-success'>Añadir al Carro</a></td>");
                }
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");

            // Esta clase se encarga de centrar los botones de navegación
            out.println("<div class='text-center mt-4'>");
            // Esta clase se encarga de crear un botón con borde verde (Floral)
            out.println("<a href='" + req.getContextPath() + "/index.html' class='btn btn-outline-success me-2'>Volver al inicio</a>");
            if (usernameOptional.isPresent()) {
                // Esta clase se encarga de crear un botón para ir al carrito
                out.println("<a href='" + req.getContextPath() + "/carro.jsp' class='btn btn-outline-info me-2'>Ver Carrito</a>");
                // Esta clase se encarga de crear un botón de color rojo (peligro)
                out.println("<a href='" + req.getContextPath() + "/logout' class='btn btn-danger'>Cerrar sesión</a>");
            }
            out.println("</div>");

            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}