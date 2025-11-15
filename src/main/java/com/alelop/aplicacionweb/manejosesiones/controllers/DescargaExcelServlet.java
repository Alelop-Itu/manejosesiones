package com.alelop.aplicacionweb.manejosesiones.controllers;
/*
 * Autor: Alejandra López
 * Fecha: 14/11/2025
 * Descripción: Esta clase se encarga de generar un archivo Excel (CSV)
 * con el contenido actual del carrito de compras.
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

@WebServlet("/descarga-excel")
public class DescargaExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Se utiliza este método para obtener el carrito de compras de la sesión
        HttpSession session = req.getSession(false);
        DetalleCarro detalleCarro = (session != null) ? (DetalleCarro) session.getAttribute("carro") : null;

        if (detalleCarro == null || detalleCarro.getItems().isEmpty()) {
            // Si el carrito está vacío, se redirige o se muestra un error
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "El carrito está vacío, no se puede descargar el Excel.");
            return;
        }

        // Estas clases se encargan de configurar la respuesta para forzar la descarga de un archivo CSV (Excel)
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment;filename=productos_carrito.csv");

        try (PrintWriter out = resp.getWriter()) {
            // Esta clase se encarga de escribir la cabecera del archivo CSV
            out.println("ID Producto;Nombre;Precio Unitario;Cantidad;Subtotal");

            // Se utiliza este método para iterar sobre los productos y escribir cada fila
            for (ItemCarro item : detalleCarro.getItems()) {
                out.printf("%d;%s;%.2f;%d;%.2f%n",
                        item.getProducto().getIdProducto(),
                        item.getProducto().getNombre(),
                        item.getProducto().getPrecio(),
                        item.getCantidad(),
                        item.getSubtotal());
            }

            // Se utiliza este método para agregar el total final al archivo CSV
            out.printf(";;;;Total: %.2f%n", detalleCarro.getTotal());
        }
    }
}