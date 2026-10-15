/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author Victor
 */
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String municipio;
    private String contrasena;
    private boolean estado;

    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static int contadorId = 1;
    // Archivo persistente: se conserva aunque se cierre el navegador o se reinicie el servidor.
    private static final Path ARCHIVO_USUARIOS = Paths.get(
            System.getProperty("user.home"), "TrazaCafeWeb", "usuarios.dat");

    static {
        cargarUsuarios();
    }

    public boolean registrar() {
        synchronized (Usuario.class) {
            if (buscarPorCorreo(this.correo) != null) {
                return false;
            }
            this.idUsuario = contadorId++;
            this.contrasena = hashPassword(this.contrasena);
            this.estado = true;
            usuarios.add(this);
            guardarUsuarios();
            return true;
        }
    }

    public boolean autenticar() {
        Usuario encontrado = buscarPorCorreo(this.correo);
        if (encontrado == null) {
            return false;
        }
        return encontrado.isEstado() && encontrado.getContrasena().equals(hashPassword(this.contrasena));
    }

    /**
     * Devuelve el usuario registrado si el correo y la contraseña son
     * correctos; si no, null.
     */
    public static Usuario iniciarSesion(String correo, String contrasena) {
        synchronized (Usuario.class) {
            Usuario u = buscarPorCorreo(correo);
            if (u == null || !u.isEstado()) {
                return null;
            }
            return u.getContrasena().equals(hashPassword(contrasena)) ? u : null;
        }
    }

    public static Usuario buscarPorId(int id) {
        synchronized (Usuario.class) {
            for (Usuario u : usuarios) {
                if (u.getIdUsuario() == id) {
                    return u;
                }
            }
            return null;
        }
    }

    /**
     * Crea la subclase según el rol ("Productor", "Catador" o "Administrador").
     */
    public static Usuario crearPorRol(String rol) {
        switch (rol) {
            case "Productor":
                return new Productor();
            case "Catador":
                return new Catador();
            case "Administrador":
                return new Administrador();
            default:
                return null;
        }
    }

    public String getRol() {
        return getClass().getSimpleName();
    }

    public void actualizarDatos() {
    }

    /**
     * Actualiza los datos básicos del usuario sin modificar su contraseña ni su
     * rol.
     */
    public synchronized boolean actualizarAdministracion(String nombre, String correo, String telefono, String municipio) {
        if (nombre == null || nombre.isBlank() || correo == null || correo.isBlank()) {
            return false;
        }
        Usuario otro = buscarPorCorreo(correo);
        if (otro != null && otro != this) {
            return false;
        }
        this.nombre = nombre.trim();
        this.correo = correo.trim();
        this.telefono = telefono == null ? "" : telefono.trim();
        this.municipio = municipio == null ? "" : municipio.trim();
        guardarUsuarios();
        return true;
    }

    /**
     * Desactiva una cuenta sin borrar sus datos.
     */
    public synchronized void desactivar() {
        this.estado = false;
        guardarUsuarios();
    }

    public synchronized void activar() {
        this.estado = true;
        guardarUsuarios();
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

    /** Guarda los usuarios registrados en disco para no perderlos al reiniciar. */
    private static synchronized void guardarUsuarios() {
        try {
            Path carpeta = ARCHIVO_USUARIOS.getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            Path temporal = ARCHIVO_USUARIOS.resolveSibling(ARCHIVO_USUARIOS.getFileName() + ".tmp");
            try (ObjectOutputStream salida = new ObjectOutputStream(Files.newOutputStream(temporal))) {
                salida.writeObject(usuarios);
            }
            try {
                Files.move(temporal, ARCHIVO_USUARIOS,
                        StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException ex) {
                Files.move(temporal, ARCHIVO_USUARIOS, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            System.err.println("No se pudieron guardar los usuarios: " + ex.getMessage());
        }
    }

    /** Recupera los usuarios guardados al iniciar la aplicación. */
    @SuppressWarnings("unchecked")
    private static synchronized void cargarUsuarios() {
        if (!Files.exists(ARCHIVO_USUARIOS)) {
            return;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(ARCHIVO_USUARIOS))) {
            Object datos = entrada.readObject();
            if (datos instanceof ArrayList<?>) {
                usuarios = (ArrayList<Usuario>) datos;
                contadorId = 1;
                for (Usuario u : usuarios) {
                    if (u != null && u.getIdUsuario() >= contadorId) {
                        contadorId = u.getIdUsuario() + 1;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            System.err.println("No se pudieron cargar los usuarios guardados: " + ex.getMessage());
        }
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
