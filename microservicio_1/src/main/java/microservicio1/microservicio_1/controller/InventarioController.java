package microservicio1.microservicio_1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import microservicio1.microservicio_1.assemblers.InventarioModelAssembler;
import microservicio1.microservicio_1.model.Inventario;
import microservicio1.microservicio_1.service.InventarioService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    private final InventarioService inventarioService;
    private final InventarioModelAssembler inventarioModelAssembler;

    public InventarioController(InventarioService inventarioService, 
                              InventarioModelAssembler inventarioModelAssembler) {
        this.inventarioService = inventarioService;
        this.inventarioModelAssembler = inventarioModelAssembler;
    }

    @Operation(summary = "Crea un nuevo registro de inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Inventario>> crearInventario(@RequestBody Inventario inventario) {
        Inventario nuevoInventario = inventarioService.crearInventario(inventario);
        return ResponseEntity
                .created(linkTo(methodOn(InventarioController.class).obtenerPorId(nuevoInventario.getId())).toUri())
                .body(inventarioModelAssembler.toModel(nuevoInventario));
    }

    @Operation(summary = "Elimina un registro de inventario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Registro eliminado"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualiza un registro de inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro actualizado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Inventario>> actualizarInventario(
            @PathVariable Long id, 
            @RequestBody Inventario inventario) {
        Inventario inventarioActualizado = inventarioService.actualizarInventario(id, inventario);
        return ResponseEntity.ok(inventarioModelAssembler.toModel(inventarioActualizado));
    }

    @Operation(summary = "Obtiene un registro de inventario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Inventario>> obtenerPorId(@PathVariable Long id) {
        Inventario inventario = inventarioService.obtenerInventarioPorId(id);
        return ResponseEntity.ok(inventarioModelAssembler.toModel(inventario));
    }

    @Operation(summary = "Obtiene todos los registros de inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "204", description = "No hay registros")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> listarInventario() {
        List<Inventario> inventarios = inventarioService.obtenerTodosLosInventarios();
        
        if (inventarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(inventarioModelAssembler.toCollectionModel(inventarios));
    }
}