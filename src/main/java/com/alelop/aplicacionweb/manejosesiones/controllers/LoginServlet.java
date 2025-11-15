package com.alelop.aplicacionweb.manejosesiones.controllers;
/*
 * Autor: Alejandra López
 * Fecha: 11/11/2025
 * Descripción: Esta clase denominada LoginServlet,
 * se encarga de modelar el servlet de Producto, clase hija de HttpServlet
 * que va a manejar el inicio de sesión
 */


import com.alelop.aplicacionweb.manejosesiones.services.LoginService;
import com.alelop.aplicacionweb.manejosesiones.services.LoginServiceSessionImplement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

// Anota el servlet con los paths de URL a los que responde
@WebServlet({"/login", "/login.html"})
// Define la clase LoginServlet que extiende HttpServlet
public class LoginServlet extends HttpServlet {
    // Declaramos e inicializamos una variable constante para el nombre de usuario
    final static String USERNAME = "admin";
    // Declaramos e inicializamos una variable constante para la contraseña
    final static String PASSWORD = "12345";

    // Sobreescribe los métodos doGet para manejar peticiones GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LoginService auth = new LoginServiceSessionImplement();
        // Obtenemos la información que está dentro de la Session usando un auth
        Optional<String> usernameOptional = auth.getUsername(req);

        // Se utiliza este método para obtener el contador de ingresos de la sesión
        // Se usa req.getSession(false) para no crear una sesión si no existe
        HttpSession session = req.getSession(false);
        Integer contadorIngresos = (session != null) ? (Integer) session.getAttribute("contadorIngresos") : null;

        // Crea un condicional para preguntar si el usuario ya ha iniciado sesión previamente
        if (usernameOptional.isPresent()) {
            // Establece el tipo de contenido y la codificación de la respuesta
            resp.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                // Comienza la generación del documento HTML
                out.print("<!DOCTYPE html>");
                out.println("<html lang=\"es\">");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                // Asegura la correcta visualización en dispositivos móviles (viewport)
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                // Agrega el enlace a la hoja de estilos de Bootstrap 5
                out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
                // Presenta el título, incluyendo el nombre de usuario
                out.println("<title> Hola: " + usernameOptional.get() + "</title>");
                out.println("</head>");
                // Usa clases de Bootstrap para un fondo verde claro (floral) y centrar el contenido
                out.println("<body class=\"bg-success-subtle d-flex align-items-center justify-content-center\" style=\"min-height: 100vh;\">");
                // Esta clase se encarga de crear una tarjeta con sombra y borde verde (floral)
                out.println("<div class=\"container text-center p-5 bg-white shadow-lg rounded-3 border-success\" style='max-width: 500px;'>");
                // Esta clase se encarga de usar el color verde de éxito (success) y centrar el título
                out.println("<h1 class=\"mb-4 text-success\">Bienvenido de vuelta</h1>");
                // Esta clase se encarga de mostrar un mensaje de éxito con fondo verde
                out.println("<h3 class=\"alert alert-success\"> Login exitoso: " + usernameOptional.get() + ", has iniciado sesión con éxito. </h3>");

                // Se utiliza este método para mostrar el contador de ingresos si existe
                if (contadorIngresos != null && contadorIngresos > 0) {
                    out.println("<p class='mb-4'><small>Esta es tu visita número: <strong>" + contadorIngresos + "</strong></small></p>");
                }

                // Botones de navegación
                out.println("<div class=\"mt-4\">");
                out.println("<a href = '" + req.getContextPath() + "/productos' class='btn btn-success me-2'> Ver Productos </a>");
                out.println("<a href = '" + req.getContextPath() + "/logout' class='btn btn-danger'> Cerrar Sesión </a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
            // Si no existe la cookie (no ha iniciado sesión)
        } else {
            // la petición se dirige  a login.jsp
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    // Sobreescribe el método doPost para manejar peticiones POST (envío de formulario)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // se usa USER que es el nombre del input en login.jsp
        String username = req.getParameter("user");
        // variable para procesar la contraseña del formulario
        String password = req.getParameter("password");

        // Credencial para verificar si las credenciales son correctas
        if (username != null && USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Obtener o crear la sesión
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Se utiliza este método para implementar el contador de ingresos
            Integer contador = (Integer) session.getAttribute("contadorIngresos");
            // Se utiliza este método para inicializar el contador si es la primera vez
            if (contador == null) {
                contador = 0;
            }
            // Esta clase se encarga de incrementar y guardar el nuevo valor del contador
            session.setAttribute("contadorIngresos", ++contador);

            // Redirección corregida al listado de productos
            resp.sendRedirect(req.getContextPath() + "/productos");

            // Si las credenciales son incorrectas
        } else {
            // Envía un código de error de No Autorizado (401) con un mensaje
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos, no está autorizado para ingresar a esta página. Revisa tus credenciales.");
        }
    }

}