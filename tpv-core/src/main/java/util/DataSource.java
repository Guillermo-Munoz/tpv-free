package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DataSource {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = DataSource.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("🔎 Classpath entries:");
                String[] paths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
                for (String p : paths) System.out.println("  " + p);
                throw new RuntimeException("❌ No se encontró database.properties en el classpath");
            }
            props.load(input);
            System.out.println("✅ Configuración cargada desde database.properties");
        } catch (IOException e) {
            throw new RuntimeException("❌ Error cargando database.properties", e);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver de MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ Driver de MySQL no encontrado", e);
        }
    }


    private DataSource() {
        // evitar instancias
    }

    public static Connection getConnection() throws SQLException {
        String url = props.getProperty("db.url");
        String usuario = props.getProperty("db.usuario");
        String password = props.getProperty("db.password");
        return DriverManager.getConnection(url, usuario, password);
    }
}
