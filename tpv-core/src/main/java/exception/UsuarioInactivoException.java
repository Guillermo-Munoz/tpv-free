package exception;

public class UsuarioInactivoException extends RuntimeException {
    public UsuarioInactivoException(String username) {
        super("Usuario '" + username + "' está inactivo");
    }
}