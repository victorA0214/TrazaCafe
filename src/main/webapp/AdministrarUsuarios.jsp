<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="Lib/Header.jsp"%>
<% if (session.getAttribute("usuario") == null || !"Administrador".equals(session.getAttribute("rol"))) {
        response.sendRedirect(request.getContextPath() + "/IniciarSesion.jsp");
        return;
    } %>
<section class="page-heading">
    <p class="eyebrow">CONTROL DE ACCESO</p>
    <h1>Administrar usuarios</h1>
    <p>Solo los administradores pueden editar los datos y activar o desactivar cuentas.</p>
</section>
<div class="table-wrap">
    <table>
        <thead><tr><th>Usuario</th><th>Correo</th><th>Rol</th><th>Estado</th><th>Editar datos</th><th>Acceso</th></tr></thead>
        <tbody>
            <% java.util.List<Modelo.Usuario> usuarios = (java.util.List<Modelo.Usuario>) request.getAttribute("usuarios");
    if (usuarios != null && !usuarios.isEmpty()) {
        for (Modelo.Usuario u : usuarios) {%>
            <tr>
                <td><strong><%=u.getNombre()%></strong><br><small>ID: <%=u.getIdUsuario()%></small></td>
                <td><%=u.getCorreo()%></td><td><%=u.getRol()%></td>
                <td><span class="status <%=u.isEstado() ? "status-active" : "status-inactive"%>"><%=u.isEstado() ? "Activo" : "Inactivo"%></span></td>
                <td>
                    <form action="<%=request.getContextPath()%>/ServletAdministracion" method="post" class="admin-edit-form">
                        <input type="hidden" name="accion" value="actualizar"><input type="hidden" name="idUsuario" value="<%=u.getIdUsuario()%>">
                        <label>Nombre<input name="nombre" required maxlength="120" value="<%=u.getNombre()%>"></label>
                        <label>Correo<input type="email" name="correo" required maxlength="150" value="<%=u.getCorreo()%>"></label>
                        <label>Teléfono<input name="telefono" maxlength="30" value="<%=u.getTelefono() == null ? "" : u.getTelefono()%>"></label>
                        <label>Municipio<input name="municipio" maxlength="100" value="<%=u.getMunicipio() == null ? "" : u.getMunicipio()%>"></label>
                        <button class="button small" type="submit">Guardar cambios</button>
                    </form>
                </td>
                <td>
                    <% if (u.isEstado()) {%>
                    <form action="<%=request.getContextPath()%>/ServletAdministracion" method="post" onsubmit="return confirm('¿Desactivar este usuario?')">
                        <input type="hidden" name="accion" value="desactivar"><input type="hidden" name="idUsuario" value="<%=u.getIdUsuario()%>">
                        <button class="button danger small" type="submit">Desactivar</button></form>
                        <% } else {%>
                    <form action="<%=request.getContextPath()%>/ServletAdministracion" method="post">
                        <input type="hidden" name="accion" value="activar"><input type="hidden" name="idUsuario" value="<%=u.getIdUsuario()%>">
                        <button class="button small" type="submit">Activar</button></form>
                        <% } %>
                </td>
            </tr>
            <% }
} else { %><tr><td colspan="6" class="empty">Todavía no hay usuarios registrados.</td></tr><% }%>
        </tbody></table></div>
        <%@include file="Lib/Footer.jsp"%>
