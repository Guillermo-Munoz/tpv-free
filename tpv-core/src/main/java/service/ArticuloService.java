package service;

import dao.ArticuloDAO;
import dao.ArticuloDAOImpl;
import model.Articulo;
import java.util.List;

public class ArticuloService {
    private ArticuloDAO  articuloDAO;

    public ArticuloService() {
        this.articuloDAO = new ArticuloDAOImpl();
    }

    public Articulo buscarArticulo(int codArticulo) {
        return articuloDAO.buscarPorCodigo(codArticulo);
    }
    public List<Articulo> listarArticulos() {
        return articuloDAO.buscarTodo();
    }
}
