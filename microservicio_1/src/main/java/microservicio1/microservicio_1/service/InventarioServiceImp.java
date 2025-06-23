package microservicio1.microservicio_1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservicio1.microservicio_1.model.Inventario;
import microservicio1.microservicio_1.repository.InventarioRepository;

@Service
public class InventarioServiceImp implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public Inventario crearInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

 

    @Override
    public Inventario obtenerInventarioPorId(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    @Override
    public Inventario actualizarInventario(Long id, Inventario inventario) {
        Inventario existente = inventarioRepository.findById(id).orElse(null);
        if (existente != null) {
            // Asegúrate de tener estos métodos en tu clase Inventario
            existente.setNombre(inventario.getNombre());
            existente.setCantidad(inventario.getCantidad());
            existente.setStock(inventario.isStock());
            existente.setTipoDeProducto(inventario.getTipoDeProducto());
            return inventarioRepository.save(existente);
        } else {
            return null;
        }
    }
    @Override
public List<Inventario> obtenerTodosLosInventarios() {
    return inventarioRepository.findAll(); // Asumiendo que usas JPA
}

    
}
