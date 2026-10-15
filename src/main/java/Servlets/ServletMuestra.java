package Servlets;

import Modelo.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/ServletMuestra")
public class ServletMuestra extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest r, HttpServletResponse s) throws IOException {
        if (!sesion(r)) {
            volver(r, s, "IniciarSesion.jsp");
            return;
        }
        try {
            r.setAttribute("fincas", Almacen.listarFincasActivas());
            r.getRequestDispatcher("/RegistrarMuestras.jsp").forward(r, s);
        } catch (Exception e) {
            mensaje(r, "error", "No se pudieron cargar las fincas.");
            volver(r, s, "PanelGeneral.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest r, HttpServletResponse s) throws IOException {
        if (!sesion(r)) {
            volver(r, s, "IniciarSesion.jsp");
            return;
        }
        r.setCharacterEncoding("UTF-8");
        try {
            String lote = v(r, "lote"), variedad = v(r, "variedad"), fecha = v(r, "fechaRecoleccion");
            Finca finca = Almacen.buscarFinca(entero(v(r, "idFinca"), 0));
            if (lote.isBlank() || variedad.isBlank() || fecha.isBlank() || finca == null || !finca.isEstado()) {
                throw new IllegalArgumentException("Datos inválidos");
            }
            Muestra m = new Muestra();
            m.setLote(lote);
            m.setVariedad(variedad);
            m.setFechaRecoleccion(LocalDate.parse(fecha));
            m.setProcesoBeneficio(v(r, "procesoBeneficio"));
            m.setCantidadProducida(v(r, "cantidadProducida"));
            m.setEstado("Pendiente");
            m.setFinca(finca);
            if (Almacen.agregarMuestra(m)) {
                mensaje(r, "ok", "Muestra registrada correctamente.");
            } else {
                mensaje(r, "error", "Ya existe una muestra con ese código de lote.");
            }
        } catch (Exception e) {
            mensaje(r, "error", "No se pudo registrar la muestra. Verifica la fecha y que la finca exista.");
        }
        volver(r, s, "ServletMuestra");
    }
}
