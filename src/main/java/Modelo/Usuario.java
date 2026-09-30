/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author Victor
 */
public abstract class Usuario {

    private int idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String municipio;
    private String contrasena;
    private boolean estado;

    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static int contadorId = 1;

    public boolean registrar() {
        if (buscarPorCorreo(this.correo) != null) {
            return false;
        }
        this.idUsuario = contadorId++;
        this.contrasena = hashPassword(this.contrasena);
        this.estado = true;
        usuarios.add(this);
        return true;

    }

    public boolean autenticar() {
        Usuario encontrado = buscarPorCorreo(this.correo);
        if (encontrado == null) {
            return false;
        }
        return encontrado.getContrasena().equals(hashPassword(this.contrasena));
    }

    public void actualizarDatos() {
    }

    public void consultar() {
    }

    public static Usuario buscarPorCorreo(String correo) {
        for (Usuario u : usuarios) {
            if (u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null;
    }

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    private static String hashPassword(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return contrasena;
        }
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
