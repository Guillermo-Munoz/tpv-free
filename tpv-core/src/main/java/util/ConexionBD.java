package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection conexion = null;

    public static Connection getConexion() throws SQLException{
        if(conexion == null) {
            String url = "jbdc:mysql://localhost:33067tpv";
            String usuario = "";
            String password = "";
            conexion = DriverManager.getConnection(url, usuario, password);
        }
        return conexion;
    }
    public static void cerrarConexion() throws SQLException {
        if(conexion != null) {
            conexion.close();
        }
    }
}
