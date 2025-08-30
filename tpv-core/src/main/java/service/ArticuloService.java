package service;

import dao.ArticuloDAO;
import model.Articulo;
import java.util.List;
import java.util.Optional;
import exception.ArticuloNotFoundException;
import exception.StockInsuficienteException;

public class ArticuloService {
    private final ArticuloDAO articuloDAO;

    public ArticuloService(ArticuloDAO articuloDAO) {
        this.articuloDAO = articuloDAO;
    }

    // OPERACIONES CRUD BÁSICAS

    /**
     * Obtiene un artículo por su código
     */
    public Articulo obtenerArticulo(int codArticulo) {
        return articuloDAO.buscarPorCodigo(codArticulo)
                .orElseThrow(() -> new ArticuloNotFoundException(codArticulo));
    }

    /**
     * Obtiene todos los artículos
     */
    public List<Articulo> obtenerTodosArticulos() {
        return articuloDAO.buscarTodos();
    }

    /**
     * Crea un nuevo artículo
     */
    public boolean crearArticulo(Articulo articulo) {
        // Validar que no exista ya el código
        if (articuloDAO.existe(articulo.getCodArticulo())) {
            throw new IllegalArgumentException("Ya existe un artículo con código: " + articulo.getCodArticulo());
        }

        // Validaciones de negocio
        validarArticulo(articulo);

        return articuloDAO.insertar(articulo);
    }

    /**
     * Actualiza un artículo existente
     */
    public boolean actualizarArticulo(Articulo articulo) {
        // Validar que exista el artículo
        if (!articuloDAO.existe(articulo.getCodArticulo())) {
            throw new ArticuloNotFoundException(articulo.getCodArticulo());
        }

        validarArticulo(articulo);

        return articuloDAO.actualizar(articulo);
    }

    /**
     * Elimina un artículo
     */
    public boolean eliminarArticulo(int codArticulo) {
        if (!articuloDAO.existe(codArticulo)) {
            throw new ArticuloNotFoundException(codArticulo);
        }

        return articuloDAO.eliminar(codArticulo);
    }

    // OPERACIONES DE INVENTARIO

    /**
     * Reduce el stock de un artículo (para ventas)
     */
    public boolean reducirStock(int codArticulo, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reducir debe ser positiva");
        }

        Articulo articulo = obtenerArticulo(codArticulo);

        // Validar stock suficiente
        if (articulo.getStock() < cantidad) {
            throw new StockInsuficienteException(codArticulo, articulo.getStock(), cantidad);
        }

        return articuloDAO.actualizarStock(codArticulo, -cantidad);
    }

    /**
     * Aumenta el stock de un artículo (para reposiciones)
     */
    public boolean aumentarStock(int codArticulo, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a aumentar debe ser positiva");
        }

        return articuloDAO.actualizarStock(codArticulo, cantidad);
    }

    /**
     * Establece el stock a un valor específico
     */
    public boolean establecerStock(int codArticulo, int nuevoStock) {
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        Articulo articulo = obtenerArticulo(codArticulo);
        int diferencia = nuevoStock - articulo.getStock();

        return articuloDAO.actualizarStock(codArticulo, diferencia);
    }

    // OPERACIONES DE BÚSQUEDA

    /**
     * Busca artículos por nombre (búsqueda parcial)
     */
    public List<Articulo> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de búsqueda no puede estar vacío");
        }

        return articuloDAO.buscarPorNombre(nombre.trim());
    }

    /**
     * Busca artículos por categoría
     */
    public List<Articulo> buscarPorCategoria(int codCategoria) {
        return articuloDAO.buscarPorCategoria(codCategoria);
    }

    /**
     * Obtiene artículos con stock bajo
     */
    public List<Articulo> obtenerArticulosStockBajo(int stockMinimo) {
        if (stockMinimo < 0) {
            throw new IllegalArgumentException("El stock mínimo no puede ser negativo");
        }

        return articuloDAO.buscarConStockBajo(stockMinimo);
    }

    //  OPERACIONES DE PRECIOS

    /**
     * Obtiene el precio de un artículo
     */
    public double obtenerPrecio(int codArticulo) {
        return articuloDAO.obtenerPrecio(codArticulo);
    }

    /**
     * Calcula el precio con IVA de un artículo
     */
    public double obtenerPrecioConIva(int codArticulo) {
        Articulo articulo = obtenerArticulo(codArticulo);
        return articulo.getPrecio() * (1 + articulo.getPorcentajeIva() / 100);
    }

    /**
     * Actualiza el precio de un artículo
     */
    public boolean actualizarPrecio(int codArticulo, double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        Articulo articulo = obtenerArticulo(codArticulo);
        articulo.setPrecio(nuevoPrecio);

        return articuloDAO.actualizar(articulo);
    }

    //  OPERACIONES DE CONSULTA

    /**
     * Verifica si un artículo existe
     */
    public boolean existeArticulo(int codArticulo) {
        return articuloDAO.existe(codArticulo);
    }

    /**
     * Obtiene el número total de artículos
     */
    public int contarArticulos() {
        return articuloDAO.contar();
    }

    /**
     * Obtiene el valor total del inventario
     */
    public double calcularValorTotalInventario() {
        return obtenerTodosArticulos().stream()
                .mapToDouble(articulo -> articulo.getPrecio() * articulo.getStock())
                .sum();
    }

    //  VALIDACIONES DE NEGOCIO

    private void validarArticulo(Articulo articulo) {
        if (articulo.getNombre() == null || articulo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del artículo es obligatorio");
        }

        if (articulo.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (articulo.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        if (articulo.getPorcentajeIva() < 0) {
            throw new IllegalArgumentException("El porcentaje de IVA no puede ser negativo");
        }
    }

    public String obtenerInfoArticulo(int codArticulo) {
        Articulo articulo = obtenerArticulo(codArticulo);

        return String.format(
                "Artículo [%d] %s | Precio: %.2f€ (IVA: %.2f%%) | Stock: %d | Categoría: %s",
                articulo.getCodArticulo(),
                articulo.getNombre(),
                articulo.getPrecio(),
                articulo.getPorcentajeIva(),
                articulo.getStock(),
                articulo.getCategoriaId() != null ? articulo.getCategoriaId() : "Sin categoría"
        );
    }

    public String obtenerStock(int codArticulo) {
        Articulo articulo = obtenerArticulo(codArticulo);
        return String.valueOf(articulo.getStock());
    }

    public boolean tieneStockSuficiente(int codArticulo, int cantidad) {
        Articulo articulo = obtenerArticulo(codArticulo);
        return articulo.getStock() >= cantidad;
    }

    public List<Articulo> obtenerArticulosDisponibles() {
        return articuloDAO.buscarTodos()
                .stream()
                .filter(Articulo::tieneStock) // usa tu método tieneStock()
                .toList();
    }

    public int contarArticulosDisponibles() {
        return (int) articuloDAO.buscarTodos()
                .stream()
                .filter(Articulo::tieneStock)
                .count();
    }
}

