package Servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public abstract class BaseServlet extends HttpServlet {

    protected void mensaje(HttpServletRequest r, String tipo, String texto) {
        r.getSession().setAttribute("mensajeTipo", tipo);
        r.getSession().setAttribute("mensaje", texto);
    }

    protected void volver(HttpServletRequest r, HttpServletResponse s, String pagina) throws IOException {
        s.sendRedirect(r.getContextPath() + "/" + pagina);
    }

    protected String v(HttpServletRequest r, String n) {
        String x = r.getParameter(n);
        return x == null ? "" : x.trim();
    }

    protected boolean sesion(HttpServletRequest r) {
        return r.getSession(false) != null && r.getSession(false).getAttribute("usuario") != null;
    }

    protected int entero(String value, int defecto) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defecto;
        }
    }

    protected double decimal(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
