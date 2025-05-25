package microservicio1.microservicio_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import microservicio1.microservicio_1.model.Inventario;

public interface InventarioRepository extends JpaRepository <Inventario, Long>{


}
