package Servlets;

import Modelo.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ServletReporte")
public class ServletReporte extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest r, HttpServletResponse s) throws IOException {
        if (!sesion(r)) {
            volver(r, s, "IniciarSesion.jsp");
            return;
        }
        try {
            r.setAttribute("muestras", Almacen.listarMuestras());
            r.getRequestDispatcher("/Reportes.jsp").forward(r, s);
        } catch (Exception e) {
            mensaje(r, "error", "No se pudieron cargar los reportes.");
            volver(r, s, "PanelGeneral.jsp");
        }
    }
}
