package Servlets;

import Modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Herramientas de administración de cuentas, accesibles únicamente a
 * administradores.
 */
@WebServlet("/ServletAdministracion")
public class ServletAdministracion extends BaseServlet {

    private boolean esAdministrador(HttpServletRequest r) {
        return sesion(r) && "Administrador".equals(r.getSession(false).getAttribute("rol"));
    }

    @Override
    protected void doGet(HttpServletRequest r, HttpServletResponse s) throws IOException, ServletException {
        if (!esAdministrador(r)) {
            mensaje(r, "error", "Esta sección es exclusiva para administradores.");
            volver(r, s, "IniciarSesion.jsp");
            return;
        }
        r.setAttribute("usuarios", Usuario.getUsuarios());
        r.getRequestDispatcher("/AdministrarUsuarios.jsp").forward(r, s);
    }

    @Override
    protected void doPost(HttpServletRequest r, HttpServletResponse s) throws IOException {
        r.setCharacterEncoding("UTF-8");
        if (!esAdministrador(r)) {
            mensaje(r, "error", "No tienes permisos para realizar esta operación.");
            volver(r, s, "IniciarSesion.jsp");
            return;
        }
        int id = entero(v(r, "idUsuario"), -1);
        Usuario objetivo = Usuario.buscarPorId(id);
        String accion = v(r, "accion");
        if (objetivo == null) {
            mensaje(r, "error", "El usuario seleccionado no existe.");
            volver(r, s, "ServletAdministracion");
            return;
        }
        int adminId = entero(String.valueOf(r.getSession(false).getAttribute("usuarioId")), -1);
        if (id == adminId && ("desactivar".equals(accion) || "activar".equals(accion))) {
            mensaje(r, "error", "No puedes desactivar o modificar el estado de tu propia cuenta.");
            volver(r, s, "ServletAdministracion");
            return;
        }
        switch (accion) {
            case "actualizar":
                boolean ok = objetivo.actualizarAdministracion(v(r, "nombre"), v(r, "correo"),
                        v(r, "telefono"), v(r, "municipio"));
                mensaje(r, ok ? "ok" : "error", ok
                        ? "Datos del usuario actualizados."
                        : "No se pudo actualizar. Verifica el nombre, el correo y que no esté repetido.");
                break;
            case "desactivar":
                if ("Administrador".equals(objetivo.getRol()) && contarAdministradoresActivos() <= 1) {
                    mensaje(r, "error", "No puedes desactivar al último administrador activo.");
                } else {
                    objetivo.desactivar();
                    mensaje(r, "ok", "Usuario desactivado. Sus datos se conservaron.");
                }
                break;
            case "activar":
                objetivo.activar();
                mensaje(r, "ok", "Usuario activado correctamente.");
                break;
            default:
                mensaje(r, "error", "Acción administrativa no válida.");
        }
        volver(r, s, "ServletAdministracion");
    }

    private int contarAdministradoresActivos() {
        int total = 0;
        for (Usuario u : Usuario.getUsuarios()) {
            if ("Administrador".equals(u.getRol()) && u.isEstado()) {
                total++;
            }
        }
        return total;
    }
}
