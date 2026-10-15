package Servlets;

import Modelo.Usuario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ServletUsuario")
public class ServletUsuario extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest r, HttpServletResponse s) throws IOException {
        r.setCharacterEncoding("UTF-8");
        String accion = v(r, "accion");

        if ("login".equals(accion)) {
            Usuario u = Usuario.iniciarSesion(v(r, "correo"), v(r, "contrasena"));
            if (u == null) {
                mensaje(r, "error", "Correo o contraseña incorrectos.");
                volver(r, s, "IniciarSesion.jsp");
                return;
            }
            HttpSession ses = r.getSession(true);
            ses.setAttribute("usuario", u.getNombre());
            ses.setAttribute("usuarioId", u.getIdUsuario());
            ses.setAttribute("rol", u.getRol());
            ses.setAttribute("correo", u.getCorreo());
            volver(r, s, "PanelGeneral.jsp");
            return;
        }

        if ("logout".equals(accion)) {
            if (r.getSession(false) != null) {
                r.getSession(false).invalidate();
            }
            volver(r, s, "IniciarSesion.jsp");
            return;
        }

        if ("registrar".equals(accion)) {
            // Solo se permite crear el primer administrador cuando todavía no existen usuarios.
            // Después, el registro de cuentas queda reservado a una sesión de administrador.
            boolean primerUsuario = Usuario.getUsuarios().isEmpty();
            boolean esAdmin = sesion(r) && "Administrador".equals(r.getSession(false).getAttribute("rol"));
            if (!primerUsuario && !esAdmin) {
                mensaje(r, "error", "Solo un administrador puede registrar usuarios.");
                volver(r, s, "IniciarSesion.jsp");
                return;
            }
            String nombre = v(r, "nombre"), correo = v(r, "correo"), clave = v(r, "contrasena"), rol = v(r, "rol");
            if (!clave.equals(v(r, "confirmacion"))) {
                mensaje(r, "error", "Las contraseñas no coinciden.");
                volver(r, s, "RegistrarUsuario.jsp");
                return;
            }
            if (nombre.isBlank() || correo.isBlank() || clave.length() < 8
                    || !rol.matches("Productor|Catador|Administrador")
                    || (primerUsuario && !"Administrador".equals(rol))) {
                mensaje(r, "error", primerUsuario
                        ? "El primer usuario debe tener el rol Administrador. Completa los campos y usa una contraseña de al menos 8 caracteres."
                        : "Completa los campos; la contraseña debe tener al menos 8 caracteres.");
                volver(r, s, "RegistrarUsuario.jsp");
                return;
            }
            Usuario nuevo = Usuario.crearPorRol(rol);
            nuevo.setNombre(nombre);
            nuevo.setCorreo(correo);
            nuevo.setTelefono(v(r, "telefono"));
            nuevo.setMunicipio(v(r, "municipio"));
            nuevo.setContrasena(clave);
            if (nuevo.registrar()) {
                mensaje(r, "ok", "Usuario registrado correctamente.");
            } else {
                mensaje(r, "error", "Ese correo ya está registrado.");
            }
            volver(r, s, "RegistrarUsuario.jsp");
            return;
        }

        mensaje(r, "error", "Acción de usuario no válida.");
        volver(r, s, "index.jsp");
    }
}
