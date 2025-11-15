<%--
  Created by IntelliJ IDEA.
  User: ALEJANDRA
  Date: 14/11/2025
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Usuario</title>
    <%-- Se utiliza este método para enlazar la librería Bootstrap a través de un CDN --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<%-- Esta clase se encarga de dar un fondo verde muy claro (Floral), centrar el contenido vertical y horizontalmente (d-flex, align-items-center, justify-content-center), y ocupar toda la altura visible (vh-100) --%>
<body class="bg-success-subtle d-flex align-items-center justify-content-center vh-100">
<%-- Esta clase se encarga de crear un contenedor de tarjeta (card) con sombra (shadow), padding (p-4), y un borde verde (floral) --%>
<div class="card shadow-lg p-4 border-success" style="width: 24rem;">
    <%-- Esta clase se encarga de usar el color verde de éxito (success) y centrar el texto --%>
    <h1 class="text-center mb-4 text-success">🔑 Inicio de Sesión</h1>
    <%-- Esta etiqueta se encarga de enviar los datos del formulario al Servlet de login --%>
    <form action="/manejosesiones/login" method="post">
        <div class="mb-3">
            <label for="user" class="form-label">Usuario</label>
            <%-- Esta clase se encarga de aplicar los estilos de control de formulario de Bootstrap --%>
            <input type="text" name="user" id="user" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Contraseña</label>
            <%-- Esta clase se encarga de aplicar los estilos de control de formulario de Bootstrap --%>
            <input type="password" name="password" id="password" class="form-control" required>
        </div>
        <div class="d-grid">
            <%-- Esta clase se encarga de crear un botón de color verde (éxito) y que ocupe el ancho completo (d-grid) --%>
            <input type="submit" value="Iniciar Sesión" class="btn btn-success btn-lg">
        </div>
    </form>
</div>
<%-- Se utiliza este método para incluir el JavaScript de Bootstrap para componentes interactivos --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>