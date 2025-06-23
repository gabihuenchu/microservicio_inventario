package microservicio1.microservicio_1.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import microservicio1.microservicio_1.controller.InventarioController;
import microservicio1.microservicio_1.model.Inventario;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(inventario,
            // Enlace al recurso individual
            linkTo(methodOn(InventarioController.class).obtenerPorId(inventario.getId())).withSelfRel(),
            
            // Enlace a la colección completa
            linkTo(methodOn(InventarioController.class).listarInventario()).withRel("inventario"),
            
            // Enlace para eliminar
            linkTo(methodOn(InventarioController.class).eliminarInventario(inventario.getId())).withRel("eliminar"),
            
            // Enlace para actualizar (usando objeto vacío)
            linkTo(methodOn(InventarioController.class)
                .actualizarInventario(inventario.getId(), new Inventario()))
                .withRel("actualizar"),
                
            // Enlace para creación (opcional)
            linkTo(methodOn(InventarioController.class).crearInventario(null)).withRel("crear")
        );
    }

    public CollectionModel<EntityModel<Inventario>> toCollectionModel(List<Inventario> inventarios) {
        List<EntityModel<Inventario>> inventariosModel = inventarios.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(inventariosModel,
            // Enlace a sí misma
            linkTo(methodOn(InventarioController.class).listarInventario()).withSelfRel(),
            
            // Enlace para creación
            linkTo(methodOn(InventarioController.class).crearInventario(null)).withRel("crear"),
            
            // Enlace alternativo a la colección
            linkTo(methodOn(InventarioController.class).listarInventario()).withRel("inventario")
        );
    }
}
