package dao;

import model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    Optional<Usuario> buscarPorId(int id);
    Optional<Usuario> buscarPorUsername(String username);
    List<Usuario> buscarTodos();
    List<Usuario> buscarPorRol(String rol);
    List<Usuario> buscarActivo();
    boolean insertar(Usuario usuario);
    boolean actualizar(Usuario usuario);
    boolean eliminar(int id);
    boolean desactivar(int id);
    boolean existeusuario(String username);
}
