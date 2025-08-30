package dao;

import model.Articulo;
import java.util.List;
import java.util.Optional;

public interface ArticuloDAO {

    /**
     * Busca un artículo por su código único
     * @param codArticulo código del artículo a buscar
     * @return Optional con el artículo encontrado o empty si no existe
     */
    Optional<Articulo> buscarPorCodigo(int codArticulo);

    /**
     * Obtiene todos los artículos existentes en la base de datos
     * @return Lista de todos los artículos ordenados por nombre
     */
    List<Articulo> buscarTodos();

    /**
     * Busca artículos por nombre (búsqueda parcial case-insensitive)
     * @param nombre parte del nombre a buscar
     * @return Lista de artículos que coinciden con el criterio de búsqueda
     */
    List<Articulo> buscarPorNombre(String nombre);

    /**
     * Busca artículos por categoría
     * @param codCategoria código de la categoría
     * @return Lista de artículos pertenecientes a la categoría especificada
     */
    List<Articulo> buscarPorCategoria(int codCategoria);

    /**
     * Busca artículos con stock bajo (por debajo del nivel mínimo)
     * @param stockMinimo nivel mínimo de stock
     * @return Lista de artículos con stock bajo
     */
    List<Articulo> buscarConStockBajo(int stockMinimo);

    /**
     * Inserta un nuevo artículo en la base de datos
     * @param articulo artículo a insertar
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    boolean insertar(Articulo articulo);

    /**
     * Actualiza un artículo existente en la base de datos
     * @param articulo artículo con los datos actualizados
     * @return true si la actualización fue exitosa, false si el artículo no existe
     */
    boolean actualizar(Articulo articulo);

    /**
     * Elimina un artículo de la base de datos
     * @param codArticulo código del artículo a eliminar
     * @return true si la eliminación fue exitosa, false si el artículo no existe
     */
    boolean eliminar(int codArticulo);

    /**
     * Actualiza el stock de un artículo después de una venta o reposición
     * @param codArticulo código del artículo
     * @param cantidad cantidad a añadir (positiva) o restar (negativa)
     * @return true si la actualización fue exitosa
     */
    boolean actualizarStock(int codArticulo, int cantidad);

    /**
     * Verifica si existe un artículo con el código especificado
     * @param codArticulo código a verificar
     * @return true si el artículo existe, false en caso contrario
     */
    boolean existe(int codArticulo);

    /**
     * Obtiene el número total de artículos en la base de datos
     * @return cantidad total de artículos
     */
    int contar();

    /**
     * Obtiene el precio de un artículo
     * @param codArticulo código del artículo
     * @return precio del artículo o 0 si no existe
     */
    double obtenerPrecio(int codArticulo);
}