package org.tpv;


import util.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConexionBD {

    public static void main(String[] args) {
        System.out.println("🧪 Probando conexión a la base de datos...");

        try {
            // Test 1: Obtener conexión
            Connection conn = DataSource.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión exitosa!");
                System.out.println("   Base de datos: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("   Versión: " + conn.getMetaData().getDatabaseProductVersion());
            }

            // Test 2: Cerrar conexión
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("✅ Conexión cerrada correctamente");
            };

        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}