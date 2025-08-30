package org.tpv;

import dao.ArticuloDAO;
import dao.ArticuloDAOImpl;
import service.ArticuloService;
import model.Articulo;
import java.util.List;

public class TestArticuloService {

    public static void main(String[] args) {
        System.out.println("🧪 Probando ArticuloService...");

        ArticuloDAO articuloDAO = new ArticuloDAOImpl();
        ArticuloService articuloService = new ArticuloService(articuloDAO);

        try {
            // Test 1: Obtener artículo
            System.out.println("\n🔍 Obteniendo artículo 1001:");
            Articulo articulo = articuloService.obtenerArticulo(1001);
            System.out.println("✅ " + articuloService.obtenerInfoArticulo(1001));

            // Test 2: Probar stock
            System.out.println("\n📊 Probando gestión de stock:");
            System.out.println("   Stock actual: " + articuloService.obtenerStock(1001));
            System.out.println("   ¿Tiene stock? " + articuloService.tieneStockSuficiente(1001, 5));

            // Test 3: Listar artículos disponibles
            System.out.println("\n🛒 Artículos disponibles:");
            List<Articulo> disponibles = articuloService.obtenerArticulosDisponibles();
            disponibles.forEach(a -> System.out.println("   " + a.getNombre() + " - Stock: " + a.getStock()));

            // Test 4: Estadísticas
            System.out.println("\n📈 Estadísticas:");
            System.out.println("   Total artículos: " + articuloService.contarArticulos());
            System.out.println("   Artículos disponibles: " + articuloService.contarArticulosDisponibles());
            System.out.println("   Valor inventario: " + articuloService.calcularValorTotalInventario() + "€");

        } catch (Exception e) {
            System.err.println("❌ Error en pruebas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}