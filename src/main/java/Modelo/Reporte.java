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
public class Reporte {

    private int idReporte;
    private LocalDate fechaGeneracion;
    private double ountajeConsolidado;
    private String formato;
    private String estado;
    Finca finca;

    public void generar() {

    }

    public void regenerar() {

    }

    public void exportarPDF() {

    }

    public void eliminar() {

    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public double getOuntajeConsolidado() {
        return ountajeConsolidado;
    }

    public void setOuntajeConsolidado(double ountajeConsolidado) {
        this.ountajeConsolidado = ountajeConsolidado;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

