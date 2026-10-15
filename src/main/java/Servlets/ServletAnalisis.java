package Servlets;

import Modelo.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ServletAnalisis")
public class ServletAnalisis extends BaseServlet {

    private static boolean enRango(double x) {
        return x >= 0 && x <= 100;
    }

    @Override
    protected void doGet(HttpServletRequest r, HttpServletResponse s) throws IOException {
        if (!sesion(r)) {
            volver(r, s, "IniciarSesion.jsp");
            return;
        }
        String pagina = "fisico".equals(v(r, "tipo")) ? "AnalisisFisico.jsp" : "EvaluacionSensorial.jsp";
        try {
            r.setAttribute("muestras", Almacen.listarMuestras());
            r.getRequestDispatcher("/" + pagina).forward(r, s);
        } catch (Exception e) {
            mensaje(r, "error", "No se pudieron cargar las muestras.");
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
        String tipo = v(r, "tipo");
        boolean fisico = "fisico".equals(tipo);
        try {
            Muestra m = Almacen.buscarMuestra(entero(v(r, "idMuestra"), 0));
            if (m == null) {
                throw new IllegalArgumentException("Muestra inexistente");
            }
            if (fisico) {
                double humedad = decimal(v(r, "humedadFinal")), defectos = decimal(v(r, "porcentajeDefectos")),
                        trilla = decimal(v(r, "rendimientoTrilla"));
                if (!enRango(humedad) || !enRango(defectos) || !enRango(trilla)) {
                    throw new IllegalArgumentException("Fuera de rango");
                }
                ResultadoFisico rf = new ResultadoFisico();
                rf.setHumedadFinal(humedad);
                rf.setPorcentajeDefectos(defectos);
                rf.setRendimientoTrilla(trilla);
                m.setResultadoFisico(rf);
                m.setEstado("Analizada");
                mensaje(r, "ok", "Análisis físico guardado.");
            } else {
                double puntaje = decimal(v(r, "puntajeCatacion"));
                if (!enRango(puntaje)) {
                    throw new IllegalArgumentException("Fuera de rango");
                }
                ResultadoSensorial rs = new ResultadoSensorial();
                rs.setPuntajeCatacion(puntaje);
                rs.setNotasPerfilTaza(v(r, "notasPerfilTaza"));
                rs.setObservaciones(v(r, "observaciones"));
                m.setResultadoSensorial(rs);
                mensaje(r, "ok", "Evaluación sensorial guardada.");
            }
        } catch (Exception e) {
            mensaje(r, "error", "No se guardó el análisis. Comprueba el ID de muestra y los rangos (0–100).");
        }
        volver(r, s, "ServletAnalisis?tipo=" + (fisico ? "fisico" : "sensorial"));
    }
}
