package Modelo;

import java.sql.*;
import java.util.*;

/**
 * Operaciones JDBC parametrizadas para los módulos principales.
 */
public class Repositorio {

    public List<Map<String, Object>> consultar(String sql) throws SQLException {
        List<Map<String, Object>> filas = new ArrayList<>();
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> f = new LinkedHashMap<>();
                for (int i = 1; i <= n; i++) {
                    f.put(md.getColumnLabel(i), rs.getObject(i));
                }
                filas.add(f);
            }
        }
        return filas;
    }

    public int ejecutar(String sql, Object... valores) throws SQLException {
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < valores.length; i++) {
                ps.setObject(i + 1, valores[i]);
            }
            return ps.executeUpdate();
        }
    }
}
