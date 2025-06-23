package microservicio1.microservicio_1.service;

import java.util.List;


import org.springframework.stereotype.Service;

import microservicio1.microservicio_1.model.Producto;
import microservicio1.microservicio_1.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public boolean eliminarProducto(Long id) {
    if (productoRepository.existsById(id)) {
        productoRepository.deleteById(id);
        return true;
    } else {
        return false;
    }
}


}
