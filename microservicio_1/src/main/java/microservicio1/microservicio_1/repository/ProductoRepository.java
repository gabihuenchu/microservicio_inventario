package microservicio1.microservicio_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import microservicio1.microservicio_1.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
