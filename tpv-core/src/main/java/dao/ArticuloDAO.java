package dao;

import model.Articulo;
import java.util.List;

    public interface ArticuloDAO {
        Articulo buscarPorCodigo(int codArticulo);
        List<Articulo> buscarTodo();
        void insertar(Articulo articulo);
        void actualizar(Articulo articulo);
        void eliminar(int cosArticulo);
    }

