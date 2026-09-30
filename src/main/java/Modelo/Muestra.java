/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author Victor
 */
public class Muestra {

    private int idMuestra;
    private String lote;
    private String variedad;
    private LocalDate fechaRecoleccion;
    private double humedad;
    private String procesoBeneficio;
    private String cantidadProducida;
    private String estado;
    ResultadoSensorial resultadoSensorial;
    ResultadoFisico resultadoFisico;

    public void registrar() {

    }

    public void actualizarEstado() {

    }

    public void eliminar() {

    }

    public int getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(int idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public LocalDate getFechaRecoleccion() {
        return fechaRecoleccion;
    }

    public void setFechaRecoleccion(LocalDate fechaRecoleccion) {
        this.fechaRecoleccion = fechaRecoleccion;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public String getProcesoBeneficio() {
        return procesoBeneficio;
    }

    public void setProcesoBeneficio(String procesoBeneficio) {
        this.procesoBeneficio = procesoBeneficio;
    }

    public String getCantidadProducida() {
        return cantidadProducida;
    }

    public void setCantidadProducida(String cantidadProducida) {
        this.cantidadProducida = cantidadProducida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
