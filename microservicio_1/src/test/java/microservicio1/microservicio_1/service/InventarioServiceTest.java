package microservicio1.microservicio_1.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import microservicio1.microservicio_1.model.Inventario;
import microservicio1.microservicio_1.repository.InventarioRepository;

@ExtendWith(MockitoExtension.class)
class InventarioServiceImpTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioServiceImp inventarioService;

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.setId(1L);
        inventario.setNombre("Producto 1");
        inventario.setTipoDeProducto("Tipo A");
        inventario.setCantidad(10);
        inventario.setStock(true);
    }

    @Test
    void crearInventario_ShouldSaveInventory() {
        // Arrange
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        // Act
        Inventario savedInventory = inventarioService.crearInventario(inventario);

        // Assert
        assertNotNull(savedInventory);
        assertEquals("Producto 1", savedInventory.getNombre());
        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    void eliminarInventario_ShouldDeleteInventory() {
        // Arrange
        doNothing().when(inventarioRepository).deleteById(1L);

        // Act
        inventarioService.eliminarInventario(1L);

        // Assert
        verify(inventarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void obtenerInventarioPorId_WhenExists_ShouldReturnInventory() {
        // Arrange
        when(inventarioRepository.findById(1L)).thenReturn(java.util.Optional.of(inventario));

        // Act
        Inventario foundInventory = inventarioService.obtenerInventarioPorId(1L);

        // Assert
        assertNotNull(foundInventory);
        assertEquals("Producto 1", foundInventory.getNombre());
    }

    @Test
    void obtenerInventarioPorId_WhenNotExists_ShouldReturnNull() {
        // Arrange
        when(inventarioRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act
        Inventario foundInventory = inventarioService.obtenerInventarioPorId(1L);

        // Assert
        assertNull(foundInventory);
    }

    @Test
    void actualizarInventario_WhenExists_ShouldUpdateInventory() {
        // Arrange
        Inventario updatedInventory = new Inventario();
        updatedInventory.setNombre("Producto Actualizado");
        updatedInventory.setTipoDeProducto("Tipo B");
        updatedInventory.setCantidad(20);
        updatedInventory.setStock(false);

        when(inventarioRepository.findById(1L)).thenReturn(java.util.Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(updatedInventory);

        // Act
        Inventario result = inventarioService.actualizarInventario(1L, updatedInventory);

        // Assert
        assertNotNull(result);
        assertEquals("Producto Actualizado", result.getNombre());
        assertEquals("Tipo B", result.getTipoDeProducto());
        assertEquals(20, result.getCantidad());
        assertFalse(result.isStock());
    }

    @Test
    void actualizarInventario_WhenNotExists_ShouldReturnNull() {
        // Arrange
        Inventario updatedInventory = new Inventario();
        when(inventarioRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act
        Inventario result = inventarioService.actualizarInventario(1L, updatedInventory);

        // Assert
        assertNull(result);
    }
}