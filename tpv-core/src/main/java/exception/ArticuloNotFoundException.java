package exception;

public class ArticuloNotFoundException extends RuntimeException {
    public ArticuloNotFoundException(int codArticulo) {
        super("Artículo con código " + codArticulo + " no encontrado");
    }
}