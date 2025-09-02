package exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String username) {
        super("Usuario '" + username + "' no encontrado");
    }

    public UsuarioNotFoundException(int id) {
        super("Usuario con ID " + id + " no encontrado");
    }
}