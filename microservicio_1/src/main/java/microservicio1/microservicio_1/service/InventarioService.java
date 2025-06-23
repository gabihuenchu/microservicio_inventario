package microservicio1.microservicio_1.service;

import java.util.List;

import microservicio1.microservicio_1.model.Inventario;

public interface InventarioService {
    Inventario crearInventario(Inventario inventario);
    void eliminarInventario(Long id);
    Inventario actualizarInventario(Long id, Inventario inventario);
    Inventario obtenerInventarioPorId(Long id);
    List<Inventario> obtenerTodosLosInventarios(); // Nuevo método necesario
}








