package Servlets;

import Modelo.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ServletFinca")
public class ServletFinca extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest r, HttpServletResponse s) throws IOException {
        if (!sesion(r)) {
            volver(r, s, "IniciarSesion.jsp");
            return;
        }
        try {
            r.setAttribute("fincas", Almacen.listarFincasActivas());
            r.getRequestDispatcher("/ConsultarFincas.jsp").forward(r, s);
        } catch (Exception e) {
            mensaje(r, "error", "No fue posible consultar las fincas.");
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
            String accion = v(r, "accion");
            if ("eliminar".equals(accion)) {
                if (!Almacen.desactivarFinca(entero(v(r, "idFinca"), -1))) {
                    throw new IllegalArgumentException("Finca inexistente");
                }
            } else {
                String nombre = v(r, "nombre"), municipio = v(r, "municipio"), vereda = v(r, "vereda"),
                        variedad = v(r, "variedadPredominante");
                double altitud = decimal(v(r, "altitud")), area = decimal(v(r, "areaCultivada"));
                if (nombre.isBlank() || municipio.isBlank() || altitud < 0 || area < 0) {
                    throw new IllegalArgumentException("Datos inválidos");
                }
                if ("actualizar".equals(accion)) {
                    if (!Almacen.actualizarFinca(entero(v(r, "idFinca"), -1), nombre, municipio, vereda, altitud, area, variedad)) {
                        throw new IllegalArgumentException("Finca inexistente");
                    }
                } else {
                    int idProductor = entero(v(r, "idProductor"), 0);
                    if (idProductor == 0) {
                        idProductor = (Integer) r.getSession().getAttribute("usuarioId");
                    }
                    Usuario productor = Usuario.buscarPorId(idProductor);
                    if (productor == null) {
                        throw new IllegalArgumentException("Productor inexistente");
                    }
                    Finca f = new Finca();
                    f.setNombre(nombre);
                    f.setMunicipio(municipio);
                    f.setVereda(vereda);
                    f.setAltitud(altitud);
                    f.setAreaCultivada(area);
                    f.setVariedadPredominante(variedad);
                    f.setProductor(productor);
                    Almacen.agregarFinca(f);
                }
            }
            mensaje(r, "ok", "Operación de finca completada.");
        } catch (Exception e) {
            mensaje(r, "error", "No se pudo guardar la finca. Revisa los campos y la relación con el productor.");
        }
        volver(r, s, "ServletFinca");
    }
}
