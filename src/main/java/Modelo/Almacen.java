package Modelo;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Almacenamiento en memoria con ArrayList (reemplaza a SQL Server). Los
 * usuarios se guardan en Usuario.getUsuarios(); aquí van fincas y muestras (los
 * resultados físico y sensorial viven dentro de cada Muestra). Los datos se
 * pierden al reiniciar o redesplegar la aplicación.
 */
public final class Almacen {

    private static final ArrayList<Finca> fincas = new ArrayList<>();
    private static final ArrayList<Muestra> muestras = new ArrayList<>();
    private static int contadorFinca = 1;
    private static int contadorMuestra = 1;

    private Almacen() {
    }

    // ---------------- Fincas ----------------
    public static synchronized void agregarFinca(Finca f) {
        f.setIdFinca(contadorFinca++);
        f.setEstado(true);
        fincas.add(f);
    }

    public static synchronized Finca buscarFinca(int id) {
        for (Finca f : fincas) {
            if (f.getIdFinca() == id) {
                return f;
            }
        }
        return null;
    }

    /**
     * Solo fincas activas, ordenadas por nombre.
     */
    public static synchronized ArrayList<Finca> listarFincasActivas() {
        ArrayList<Finca> activas = new ArrayList<>();
        for (Finca f : fincas) {
            if (f.isEstado()) {
                activas.add(f);
            }
        }
        activas.sort(Comparator.comparing(Finca::getNombre, String.CASE_INSENSITIVE_ORDER));
        return activas;
    }

    public static synchronized boolean actualizarFinca(int id, String nombre, String municipio, String vereda,
            double altitud, double area, String variedad) {
        Finca f = buscarFinca(id);
        if (f == null) {
            return false;
        }
        f.setNombre(nombre);
        f.setMunicipio(municipio);
        f.setVereda(vereda);
        f.setAltitud(altitud);
        f.setAreaCultivada(area);
        f.setVariedadPredominante(variedad);
        return true;
    }

    /**
     * Eliminación lógica: la finca queda inactiva, igual que antes (estado=0).
     */
    public static synchronized boolean desactivarFinca(int id) {
        Finca f = buscarFinca(id);
        if (f == null) {
            return false;
        }
        f.setEstado(false);
        return true;
    }

    // ---------------- Muestras ----------------
    /**
     * Devuelve false si ya existe una muestra con el mismo lote (el lote es
     * único).
     */
    public static synchronized boolean agregarMuestra(Muestra m) {
        for (Muestra x : muestras) {
            if (x.getLote().equalsIgnoreCase(m.getLote())) {
                return false;
            }
        }
        m.setIdMuestra(contadorMuestra++);
        muestras.add(m);
        return true;
    }

    public static synchronized Muestra buscarMuestra(int id) {
        for (Muestra m : muestras) {
            if (m.getIdMuestra() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Todas las muestras, la más reciente primero (por fecha de recolección).
     */
    public static synchronized ArrayList<Muestra> listarMuestras() {
        ArrayList<Muestra> copia = new ArrayList<>(muestras);
        copia.sort(Comparator.comparing(Muestra::getFechaRecoleccion).reversed());
        return copia;
    }
}
