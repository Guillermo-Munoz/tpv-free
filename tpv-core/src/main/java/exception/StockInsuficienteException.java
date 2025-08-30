package exception;


public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(int codArticulo, int stockActual, int stockRequerido) {
        super(String.format("Stock insuficiente para artículo %d. Stock actual: %d, Stock requerido: %d",
                codArticulo, stockActual, stockRequerido));
    }
}