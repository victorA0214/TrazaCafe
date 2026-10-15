package Modelo;

import java.sql.*;

/**
 * Conexión JDBC a SQL Server. Por defecto usa el usuario trazacafe_app creado
 * por database/TrazaCafe_SQLServer.sql (contraseña de desarrollo local). Para
 * otra configuración define TRAZACAFE_DB_URL, TRAZACAFE_DB_USER y
 * TRAZACAFE_DB_PASSWORD.
 */
public final class DB {

    private DB() {
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("TRAZACAFE_DB_URL");
        String user = System.getenv("TRAZACAFE_DB_USER");
        String pass = System.getenv("TRAZACAFE_DB_PASSWORD");
        if (url == null || url.isBlank()) {
            url = "jdbc:sqlserver://localhost:1433;databaseName=TrazaCafe;encrypt=true;trustServerCertificate=true;loginTimeout=10";
        }
        if (user == null) {
            user = "trazacafe_app";
        }
        if (pass == null) {
            pass = "TrazaCafe2026*";
        }
        return DriverManager.getConnection(url, user, pass);
    }

    public static String texto(String value) {
        return value == null ? "" : value.trim();
    }

    public static int entero(String value, int defecto) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defecto;
        }
    }

    public static double decimal(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
