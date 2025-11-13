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

        // Crea un condicional para preguntar si el usuario ya ha iniciado sesión previamente
        if (usernameOptional.isPresent()) {
            // Establece el tipo de contenido y la codificación de la respuesta
            resp.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                // Establece nuevamente el tipo de contenido y codificación (redundante pero incluido)
                resp.setContentType("text/html;charset=UTF-8");
                // Comienza la generación del documento HTML
                out.print("<!DOCTYPE html>");
                out.println("<html lang=\"es\">");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                // Asegura la correcta visualización en dispositivos móviles (viewport)
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                // Agrega el enlace a la hoja de estilos de Bootstrap 5
                out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
                // Presenta el título, incluyendo el cookie
                out.println("<title> Hola: " + usernameOptional.get() + "</title>");
                out.println("</head>");
                // Usa estilo boostrap para centrar el contenido
                out.println("<body class=\"bg-light d-flex align-items-center justify-content-center\" style=\"min-height: 100vh;\">");
                out.println("<div class=\"container text-center p-5 bg-white shadow-lg rounded-3\">");
                out.println("<h1 class=\"mb-4 text-success\">Bienvenido de vuelta a mi sistema</h1>");
                //Presenta un mensaje de inicio de sesión exitoso
                out.println("<h3 class=\"alert alert-success\"> Login exitoso: " + usernameOptional.get() + ", has iniciado sesión con éxito. </h3>");

                // Botones de navegación
                out.println("<div class=\"mt-4\">");
                out.println("<a href = '" + req.getContextPath() + "/index.html' class='btn btn-secondary me-2'> Volver al inicio </a>");
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

    // Sobreescribe el métodos doPost para manejar peticiones POST (envío de formulario)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // se usa USER que es el nombre del input en login.jsp
        String username = req.getParameter("user");
        // variable para procesar la contraseña del formulario
        String password = req.getParameter("password");

        // Credencial para verificar si las credenciales son correctas
        if (username != null && USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Redirección corregida al índice
            resp.sendRedirect(req.getContextPath() + "/productos");

            // Si las credenciales son incorrectas
        } else {
            // Envía un código de error de No Autorizado (401) con un mensaje
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos, no está autorizado para ingresar a esta página. Revisa tus credenciales.");
        }
    }

}
