package dao;

import model.Articulo;
import util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class ArticuloDAOImpl implements ArticuloDAO {

    @Override
    public Articulo buscarPorCodigo(int codArticulo){
        Articulo articulo = null;
        String sql = "SELECT * FROM articulo WHERE cod_articulo = ?";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codArticulo);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                articulo = new Articulo();
                articulo.setCodArticulo(rs.getInt("cod_Articulo");
                articulo.setNombre(rs.getString("nombre"));
                articulo.setStock(rs.getInt("stock"));
                articulo.setPrecio(rs.getPrecio("precio"));
                articulo.setPorcentajeIva(rs.getPorcentajeIva("porcentajeIva"));
                articulo.setImagen(rs.getImagen("imagen"));
                articulo.setCategoria(rs.getCategoria("categoria"));
            }
    } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulo;
    }

}
