package com.alelop.aplicacionweb.manejosesiones.controllers;
/*
 * Autor: Alejandra López
 * Fecha: 14/11/2025
 * Descripción: Esta clase se encarga de generar un archivo de texto plano
 * que simula la factura o resumen de compra del carrito.
 */

import com.alelop.aplicacionweb.manejosesiones.models.DetalleCarro;
import com.alelop.aplicacionweb.manejosesiones.models.ItemCarro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Esta clase se encarga de mapear la URL de descarga de la factura
@WebServlet("/descarga-factura")
public class DescargaFacturaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Se utiliza este método para obtener la sesión sin crear una nueva si no existe
        HttpSession session = req.getSession(false);
        // Se utiliza este método para obtener el carrito de compras de la sesión
        DetalleCarro detalleCarro = (session != null) ? (DetalleCarro) session.getAttribute("carro") : null;
        // Se utiliza este método para obtener el nombre de usuario de la sesión para la factura
        String username = (session != null) ? (String) session.getAttribute("username") : "Invitado";

        // Se utiliza este método para validar si existe el carrito o si está vacío
        if (detalleCarro == null || detalleCarro.getItems().isEmpty()) {
            // Si el carrito está vacío, se envía un código de error
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "El carrito está vacío, no se puede generar la factura.");
            return;
        }

        // Estas clases se encargan de configurar la respuesta para forzar la descarga de un archivo de texto
        resp.setContentType("text/plain");
        // Esta clase se encarga de establecer el nombre del archivo que se va a descargar
        resp.setHeader("Content-Disposition", "attachment;filename=factura_" + username + ".txt");

        // Se utiliza este método para obtener la fecha y hora actual para la factura
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try (PrintWriter out = resp.getWriter()) {
            // Esta clase se encarga de escribir el encabezado de la factura
            out.println("============================================");
            out.println("           FACTURA DE COMPRA ONLINE         ");
            out.println("============================================");
            out.println("Fecha y Hora: " + dtf.format(now));
            out.println("Cliente: " + username);
            out.println("--------------------------------------------");
            out.println("ID | Nombre | Precio | Cant. | Subtotal");
            out.println("--------------------------------------------");

            // Se utiliza este método para iterar sobre los productos y detallar cada línea
            for (ItemCarro item : detalleCarro.getItems()) {
                // Se utiliza printf para formatear los datos de manera limpia y tabular
                out.printf("%d | %s | %.2f | %d | %.2f%n",
                        item.getProducto().getIdProducto(),
                        item.getProducto().getNombre(),
                        item.getProducto().getPrecio(),
                        item.getCantidad(),
                        item.getSubtotal());
            }

            out.println("--------------------------------------------");
            // Se utiliza este método para agregar el total final
            out.printf("TOTAL A PAGAR: %.2f%n", detalleCarro.getTotal());
            out.println("============================================");
            out.println("¡Gracias por su compra!");
        }
    }
}