package model;

public class Articulo {
    private int codArticulo;
    private String nombre;
    private int stock;
    private double precio;
    private double porcentajeIva;
    private String imagen;
    private Integer categoriaId;

    // Constructores
    public Articulo() {}

    public Articulo(int codArticulo, String nombre, int stock, double precio,
                    double porcentajeIva, String imagen, Integer categoriaId) {
        this.codArticulo = codArticulo;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.porcentajeIva = porcentajeIva;
        this.imagen = imagen;
        this.categoriaId = categoriaId;
    }

    // Getters y Setters
    public int getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(int codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(double porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    // Métodos útiles
    public double getPrecioConIva() {
        return precio * (1 + porcentajeIva / 100);
    }

    public boolean tieneStock() {
        return stock > 0;
    }

    public boolean tieneStockSuficiente(int cantidad) {
        return stock >= cantidad;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "codArticulo=" + codArticulo +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", porcentajeIva=" + porcentajeIva +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articulo articulo = (Articulo) o;
        return codArticulo == articulo.codArticulo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codArticulo);
    }
}