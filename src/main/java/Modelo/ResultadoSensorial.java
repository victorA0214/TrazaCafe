/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Victor
 */
public class ResultadoSensorial {

    private double puntajeCatacion;
    private String notasPerfilTaza;
    private String observaciones;

    Catador catador;

    public void registrar() {

    }

    public double getPuntajeCatacion() {
        return puntajeCatacion;
    }

    public void setPuntajeCatacion(double puntajeCatacion) {
        this.puntajeCatacion = puntajeCatacion;
    }

    public String getNotasPerfilTaza() {
        return notasPerfilTaza;
    }

    public void setNotasPerfilTaza(String notasPerfilTaza) {
        this.notasPerfilTaza = notasPerfilTaza;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
