package microservicio1.microservicio_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservicio1.microservicio_1.model.Inventario;
import microservicio1.microservicio_1.repository.InventarioRepository;

@Service
public class InventarioServiceImp implements InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
    @Override
    public Inventario crearInventario (Inventario inventario){
        return inventarioRepository.save(inventario);
    }

}
