<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="Lib/Header.jsp"%>
<section class="auth-card register-card">
    <img class="auth-logo" src="<%=request.getContextPath()%>/Images/logo_completo.png" alt="Trazacafé">
    <p class="eyebrow">ADMINISTRACIÓN</p>
    <h1>Registrar usuario</h1>
    <p>Crea cuentas para los usuarios de Trazacafé. Después del primer administrador, solo un administrador puede registrar nuevas cuentas.</p>
    <form action="<%=request.getContextPath()%>/ServletUsuario" method="post" class="form-grid register-form" onsubmit="return validarClaves(this)">
        <input type="hidden" name="accion" value="registrar">
        <label class="span-2">Nombre completo<input name="nombre" required maxlength="120" autocomplete="name" placeholder="Nombre y apellidos"></label>
        <label>Correo electrónico<input type="email" name="correo" required maxlength="150" autocomplete="email" placeholder="usuario@correo.com"></label>
        <label>Teléfono<input name="telefono" type="tel" maxlength="30" autocomplete="tel" placeholder="3001234567"></label>
        <label>Municipio<input name="municipio" maxlength="100" placeholder="Pasto, La Unión, Buesaco..."></label>
        <label>Rol<select name="rol" required><option value="">Selecciona un rol</option><option>Productor</option><option>Catador</option><% if (Modelo.Usuario.getUsuarios().isEmpty() || "Administrador".equals(session.getAttribute("rol"))) { %><option>Administrador</option><% }%></select></label>
        <label>Contraseña<input type="password" name="contrasena" minlength="8" required autocomplete="new-password"><small>Mínimo 8 caracteres.</small></label>
        <label>Confirmar contraseña<input type="password" name="confirmacion" minlength="8" required autocomplete="new-password"></label>
        <p id="errorClaves" class="alert error span-2" hidden>Las contraseñas no coinciden.</p>
        <div class="form-actions span-2"><button class="button" type="submit">Guardar usuario</button><a class="button secondary" href="<%=request.getContextPath()%>/PanelGeneral.jsp">Cancelar</a></div>
    </form>
    <script>function validarClaves(f) {
        var ok = f.contrasena.value === f.confirmacion.value;
        document.getElementById('errorClaves').hidden = ok;
        return ok;
    }</script>
</section>
<%@include file="Lib/Footer.jsp"%>
