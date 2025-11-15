<%--
  Created by IntelliJ IDEA.
  User: ALEJANDRA
  Date: 14/11/2025
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.alelop.aplicacionweb.manejosesiones.models.*" %>
<%-- Se utiliza este método para obtener el objeto DetalleCarro de la sesión --%>
<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carro de Compras</title>
    <%-- Se utiliza este método para enlazar la librería Bootstrap a través de un CDN --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<%-- Esta clase se encarga de centrar el contenido y agregar un margen superior --%>
<body class="container mt-5">

<%-- Esta clase se encarga de crear un contenedor con sombra y un color de fondo verde claro (Floral) --%>
<div class="card shadow-lg border-success">
    <%-- Esta clase se encarga de usar el color de fondo verde de éxito (Floral) y texto blanco --%>
    <div class="card-header bg-success text-white">
        <%-- Esta clase se encarga de usar un encabezado grande y centrar el texto --%>
        <h1 class="text-center mb-0">🛒 Carro de Compras</h1>
    </div>
    <div class="card-body">
        <%
            if (detalleCarro == null || detalleCarro.getItems().isEmpty()) {%>
        <%-- Esta clase se encarga de mostrar una alerta de advertencia cuando el carrito está vacío --%>
        <div class="alert alert-warning text-center" role="alert">
            Lo sentimos, ¡no hay productos en el carro de compras!
        </div>
        <%} else {%>
        <%-- Esta clase se encarga de aplicar un estilo moderno de tabla de Bootstrap (striped y hover) --%>
        <table class="table table-striped table-hover table-bordered">
            <%-- Esta clase se encarga de aplicar un color de fondo verde claro para la cabecera de la tabla --%>
            <thead class="table-success">
            <tr>
                <th>ID Producto</th>
                <th>Nombre</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
            </tr>
            </thead>
            <tbody>
            <%
                for(ItemCarro item : detalleCarro.getItems()){%>
            <tr>
                <td><%=item.getProducto().getIdProducto()%></td>
                <td><%=item.getProducto().getNombre()%></td>
                <td><%=item.getProducto().getPrecio()%></td>
                <td><%=item.getCantidad()%></td>
                <td><%=item.getSubtotal()%></td>
            </tr>
            <% }%>
            </tbody>
            <%-- Esta clase se encarga de usar el pie de la tabla para el total --%>
            <tfoot>
            <tr class="fw-bold bg-light">
                <%-- Esta clase se encarga de alinear el texto a la derecha --%>
                <td colspan="4" class="text-end">Total: </td>
                <td><%=detalleCarro.getTotal()%></td>
            </tr>
            </tfoot>
        </table>

        <%-- Se utiliza este método para agrupar los botones de descarga --%>
        <div class="d-flex justify-content-end gap-2 mt-3">
            <%-- Esta clase se encarga de crear un botón para descargar el Excel. Apunta al Servlet de descarga. --%>
            <a href="<%=request.getContextPath()%>/descarga-excel" class="btn btn-outline-success">Descargar Excel Productos</a>
            <%-- Esta clase se encarga de crear un botón para descargar la factura. Apunta al Servlet de factura. --%>
            <a href="<%=request.getContextPath()%>/descarga-factura" class="btn btn-outline-primary">Descargar Factura</a>
        </div>


        <%}%>
        <div class="d-flex justify-content-between mt-4">
            <%-- Esta clase se encarga de crear un botón de color verde (éxito) --%>
            <a href="<%=request.getContextPath()%>/productos" class="btn btn-success">SEGUIR COMPRANDO</a>
            <%-- Esta clase se encarga de crear un botón de color gris (secundario) --%>
            <a href="<%=request.getContextPath()%>/index.html" class="btn btn-secondary">Volver al Inicio</a>
        </div>
    </div>
</div>

<%-- Se utiliza este método para incluir el JavaScript de Bootstrap para componentes interactivos --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>