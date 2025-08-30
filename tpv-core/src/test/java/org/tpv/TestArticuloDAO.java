package org.tpv;

import dao.ArticuloDAO;
import dao.ArticuloDAOImpl;
import model.Articulo;
import java.util.List;
import java.util.Optional;

public class TestArticuloDAO {

    public static void main(String[] args) {
        System.out.println("🧪 Probando ArticuloDAO...");

        ArticuloDAO articuloDAO = new ArticuloDAOImpl();

        try {
            // Test 1: Obtener todos los artículos
            System.out.println("\n📦 Obteniendo todos los artículos:");
            List<Articulo> articulos = articuloDAO.buscarTodos();
            articulos.forEach(System.out::println);

            // Test 2: Buscar artículo por código
            System.out.println("\n🔍 Buscando artículo con código 1001:");
            Optional<Articulo> articulo = articuloDAO.buscarPorCodigo(1001);
            if (articulo.isPresent()) {
                System.out.println("✅ Encontrado: " + articulo.get());
            } else {
                System.out.println("❌ Artículo no encontrado");
            }

            // Test 3: Contar artículos
            System.out.println("\n📊 Total de artículos: " + articuloDAO.contar());

            // Test 4: Verificar si existe artículo
            System.out.println("❓ ¿Existe artículo 1001? " + articuloDAO.existe(1001));
            System.out.println("❓ ¿Existe artículo 9999? " + articuloDAO.existe(9999));

        } catch (Exception e) {
            System.err.println("❌ Error en pruebas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}