package dao;

import model.Articulo;
import util.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticuloDAOImpl implements ArticuloDAO {

    @Override
    public Optional<Articulo> buscarPorCodigo(int codArticulo) {
        String sql = "SELECT * FROM articulo WHERE cod_articulo = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codArticulo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearArticulo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando artículo por código: " + codArticulo, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Articulo> buscarTodos() {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo ORDER BY nombre";

        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                articulos.add(mapearArticulo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo todos los artículos", e);
        }

        return articulos;
    }

    @Override
    public List<Articulo> buscarPorNombre(String nombre) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo WHERE LOWER(nombre) LIKE LOWER(?) ORDER BY nombre";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articulos.add(mapearArticulo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando artículos por nombre: " + nombre, e);
        }

        return articulos;
    }

    @Override
    public List<Articulo> buscarPorCategoria(int codCategoria) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo WHERE categoria_id = ? ORDER BY nombre";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codCategoria);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articulos.add(mapearArticulo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando artículos por categoría: " + codCategoria, e);
        }

        return articulos;
    }

    @Override
    public List<Articulo> buscarConStockBajo(int stockMinimo) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo WHERE stock < ? ORDER BY stock ASC";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockMinimo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articulos.add(mapearArticulo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando artículos con stock bajo", e);
        }

        return articulos;
    }

    @Override
    public boolean insertar(Articulo articulo) {
        String sql = "INSERT INTO articulo (cod_articulo, nombre, stock, precio, porcentaje_iva, imagen, categoria_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, articulo.getCodArticulo());
            stmt.setString(2, articulo.getNombre());
            stmt.setInt(3, articulo.getStock());
            stmt.setDouble(4, articulo.getPrecio());
            stmt.setDouble(5, articulo.getPorcentajeIva());
            stmt.setString(6, articulo.getImagen());

            // ✅ SOLUCIÓN SIMPLIFICADA - solo el ID
            if (articulo.getCategoriaId() != null) {
                stmt.setInt(7, articulo.getCategoriaId());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando artículo: " + articulo.getCodArticulo(), e);
        }
    }

    @Override
    public boolean actualizar(Articulo articulo) {
        String sql = "UPDATE articulo SET nombre = ?, stock = ?, precio = ?, porcentaje_iva = ?, " +
                "imagen = ?, categoria_id = ? WHERE cod_articulo = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, articulo.getNombre());
            stmt.setInt(2, articulo.getStock());
            stmt.setDouble(3, articulo.getPrecio());
            stmt.setDouble(4, articulo.getPorcentajeIva());
            stmt.setString(5, articulo.getImagen());

            if (articulo.getCategoriaId() != null) {
                stmt.setInt(6, articulo.getCategoriaId()); // aquí solo pones el Integer
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setInt(7, articulo.getCodArticulo());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando artículo: " + articulo.getCodArticulo(), e);
        }
    }

    @Override
    public boolean eliminar(int codArticulo) {
        String sql = "DELETE FROM articulo WHERE cod_articulo = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codArticulo);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando artículo: " + codArticulo, e);
        }
    }

    @Override
    public boolean actualizarStock(int codArticulo, int cantidad) {
        String sql = "UPDATE articulo SET stock = stock + ? WHERE cod_articulo = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, codArticulo);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando stock del artículo: " + codArticulo, e);
        }
    }

    @Override
    public boolean existe(int codArticulo) {
        String sql = "SELECT 1 FROM articulo WHERE cod_articulo = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codArticulo);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error verificando existencia del artículo: " + codArticulo, e);
        }
    }

    @Override
    public int contar() {
        String sql = "SELECT COUNT(*) FROM articulo";

        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error contando artículos", e);
        }
    }

    @Override
    public double obtenerPrecio(int codArticulo) {
        String sql = "SELECT precio FROM articulo WHERE cod_articulo = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codArticulo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("precio");
                }
                return 0.0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo precio del artículo: " + codArticulo, e);
        }
    }

    private Articulo mapearArticulo(ResultSet rs) throws SQLException {
        Articulo articulo = new Articulo();
        articulo.setCodArticulo(rs.getInt("cod_articulo"));
        articulo.setNombre(rs.getString("nombre"));
        articulo.setStock(rs.getInt("stock"));
        articulo.setPrecio(rs.getDouble("precio"));
        articulo.setPorcentajeIva(rs.getDouble("porcentaje_iva"));
        articulo.setImagen(rs.getString("imagen"));

        // El mapeo completo de categoría se haría en el servicio
        // o con un DAO adicional si es necesario

        return articulo;
    }
}