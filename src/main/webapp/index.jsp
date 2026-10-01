<%@ page contentType="text/html; charset=UTF-8"
         language="java" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Trazacafé</title>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>

        <h1>Bienvenido a Trazacafé</h1>
        <p>Selecciona la ventana que deseas abrir.</p>

        <nav>
            <a href="PanelGeneral.jsp">General</a>
            <a href="IniciarSesion.jsp">Inicio</a>
            <a href="ConsultarFincas.jsp">Fincas</a>
            <a href="Registrar_EditarFincas.jsp">Registrar finca</a>
            <a href="RegistrarMuestras.jsp">Muestras</a>
            <a href="AnalisisFisico.jsp">Resultado físico</a>
            <a href="EvaluacionSensorial.jsp">Resultado sensorial</a>
            <a href="Reportes.jsp">Reportes</a>
            <a href="RegistrarUsuario.jsp">Usuarios</a>
        </nav>

    </body>
</html>
<%@ include file= "Lib/Footer.jsp"%>