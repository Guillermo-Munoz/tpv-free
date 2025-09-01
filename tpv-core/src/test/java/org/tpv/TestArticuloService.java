package org.tpv;

import dao.ArticuloDAO;
import dao.ArticuloDAOImpl;
import service.ArticuloService;
import model.Articulo;
import java.util.List;

public class TestArticuloService {

    public static void main(String[] args) {
        System.out.println("🧪 INICIANDO PRUEBAS COMPLETAS DE ARTICULOSERVICE");
        System.out.println("=================================================");

        ArticuloDAO articuloDAO = new ArticuloDAOImpl();
        ArticuloService articuloService = new ArticuloService(articuloDAO);

        // Código de artículo de prueba (cambia si es necesario)
        int codigoPrueba = 9999;
        String nombrePrueba = "Producto de Prueba TPV";

        try {
            // 🔹 1. TEST CREAR ARTÍCULO
            System.out.println("\n🔹 1. TEST CREAR ARTÍCULO");
            Articulo nuevoArticulo = new Articulo();
            nuevoArticulo.setCodArticulo(codigoPrueba);
            nuevoArticulo.setNombre(nombrePrueba);
            nuevoArticulo.setStock(50);
            nuevoArticulo.setPrecio(15.99);
            nuevoArticulo.setPorcentajeIva(21.0);
            nuevoArticulo.setImagen("prueba.jpg");
            nuevoArticulo.setCategoriaId(1);

            boolean creado = articuloService.crearArticulo(nuevoArticulo);
            System.out.println("✅ Artículo creado: " + creado);
            System.out.println("📋 " + articuloService.obtenerInfoArticulo(codigoPrueba));

            // 🔹 2. TEST OBTENER ARTÍCULO
            System.out.println("\n🔹 2. TEST OBTENER ARTÍCULO");
            Articulo articulo = articuloService.obtenerArticulo(codigoPrueba);
            System.out.println("✅ Artículo obtenido: " + articulo.getNombre());
            System.out.println("   Código: " + articulo.getCodArticulo());
            System.out.println("   Precio: " + articulo.getPrecio() + "€");
            System.out.println("   Stock: " + articulo.getStock() + " unidades");

            // 🔹 3. TEST ACTUALIZAR ARTÍCULO
            System.out.println("\n🔹 3. TEST ACTUALIZAR ARTÍCULO");
            articulo.setPrecio(18.50);
            articulo.setStock(75);
            articulo.setNombre("Producto Actualizado TPV");

            boolean actualizado = articuloService.actualizarArticulo(articulo);
            System.out.println("✅ Artículo actualizado: " + actualizado);
            System.out.println("📋 " + articuloService.obtenerInfoArticulo(codigoPrueba));

            // 🔹 4. TEST GESTIÓN DE STOCK
            System.out.println("\n🔹 4. TEST GESTIÓN DE STOCK");
            System.out.println("📊 Stock actual: " + articuloService.obtenerStock(codigoPrueba));

            // Reducir stock (simular venta)
            boolean stockReducido = articuloService.reducirStock(codigoPrueba, 10);
            System.out.println("✅ Stock reducido en 10 unidades: " + stockReducido);
            System.out.println("📊 Stock después de venta: " + articuloService.obtenerStock(codigoPrueba));

            // Aumentar stock (simular reposición)
            boolean stockAumentado = articuloService.aumentarStock(codigoPrueba, 20);
            System.out.println("✅ Stock aumentado en 20 unidades: " + stockAumentado);
            System.out.println("📊 Stock después de reposición: " + articuloService.obtenerStock(codigoPrueba));

            // 🔹 5. TEST BÚSQUEDAS
            System.out.println("\n🔹 5. TEST BÚSQUEDAS");
            List<Articulo> resultados = articuloService.buscarPorNombre("TPV");
            System.out.println("✅ Búsqueda por 'TPV': " + resultados.size() + " resultados");
            resultados.forEach(a -> System.out.println("   📦 " + a.getNombre()));

            // 🔹 6. TEST PRECIOS E IVA
            System.out.println("\n🔹 6. TEST PRECIOS E IVA");
            double precioConIva = articuloService.obtenerPrecioConIva(codigoPrueba);
            System.out.println("💰 Precio con IVA: " + precioConIva + "€");
            System.out.println("💰 Precio sin IVA: " + articuloService.obtenerPrecio(codigoPrueba) + "€");

            // 🔹 7. TEST ESTADÍSTICAS
            System.out.println("\n🔹 7. TEST ESTADÍSTICAS");
            System.out.println("📊 Total artículos: " + articuloService.contarArticulos());
            System.out.println("📊 Artículos disponibles: " + articuloService.contarArticulosDisponibles());
            System.out.println("💰 Valor inventario: " + articuloService.calcularValorTotalInventario() + "€");

            // 🔹 8. TEST ARTÍCULOS DISPONIBLES
            System.out.println("\n🔹 8. TEST ARTÍCULOS DISPONIBLES");
            List<Articulo> disponibles = articuloService.obtenerArticulosDisponibles();
            System.out.println("🛒 Artículos con stock: " + disponibles.size());
            disponibles.forEach(a -> System.out.println("   ✅ " + a.getNombre() + " - Stock: " + a.getStock()));

            // 🔹 9. TEST VALIDACIONES
            System.out.println("\n🔹 9. TEST VALIDACIONES");
            System.out.println("❓ ¿Existe artículo " + codigoPrueba + "? " + articuloService.existeArticulo(codigoPrueba));
            System.out.println("❓ ¿Stock suficiente para 50 unidades? " + articuloService.tieneStockSuficiente(codigoPrueba, 50));
            System.out.println("❓ ¿Stock suficiente para 200 unidades? " + articuloService.tieneStockSuficiente(codigoPrueba, 200));

            // 🔹 10. TEST ELIMINAR ARTÍCULO (OPCIONAL)
            System.out.println("\n🔹 10. TEST ELIMINAR ARTÍCULO");
            boolean eliminado = articuloService.eliminarArticulo(codigoPrueba);
            System.out.println("✅ Artículo eliminado: " + eliminado);
            System.out.println("❓ ¿Existe después de eliminar? " + articuloService.existeArticulo(codigoPrueba));

            System.out.println("\n🎉 ¡TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE!");

        } catch (Exception e) {
            System.err.println("\n❌ ERROR EN PRUEBAS: " + e.getMessage());
            e.printStackTrace();

            // Intentar limpiar el artículo de prueba si hubo error
            try {
                if (articuloService.existeArticulo(codigoPrueba)) {
                    articuloService.eliminarArticulo(codigoPrueba);
                    System.out.println("🧹 Artículo de prueba eliminado por limpieza");
                }
            } catch (Exception ex) {
                System.err.println("⚠️ Error en limpieza: " + ex.getMessage());
            }
        }
    }
}