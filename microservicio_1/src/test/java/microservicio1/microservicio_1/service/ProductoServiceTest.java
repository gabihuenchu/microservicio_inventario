package microservicio1.microservicio_1.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import microservicio1.microservicio_1.model.Producto;
import microservicio1.microservicio_1.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto("Producto 1", "Descripción", 10, 1000.0);
        producto.setId(1L);
    }

    @Test
    void listarProductos_ShouldReturnAllProducts() {
        // Arrange
        List<Producto> expectedProducts = Arrays.asList(producto);
        when(productoRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Producto> actualProducts = productoService.listarProductos();

        // Assert
        assertEquals(1, actualProducts.size());
        assertEquals("Producto 1", actualProducts.get(0).getNombre());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void guardarProducto_ShouldSaveProduct() {
        // Arrange
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Act
        Producto savedProduct = productoService.guardarProducto(producto);

        // Assert
        assertNotNull(savedProduct);
        assertEquals(1L, savedProduct.getId());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void obtenerProductoPorId_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Act
        Producto foundProduct = productoService.obtenerProductoPorId(1L);

        // Assert
        assertNotNull(foundProduct);
        assertEquals("Producto 1", foundProduct.getNombre());
    }

    @Test
    void obtenerProductoPorId_WhenProductNotExists_ShouldReturnNull() {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Producto foundProduct = productoService.obtenerProductoPorId(1L);

        // Assert
        assertNull(foundProduct);
    }

    @Test
    void eliminarProducto_WhenProductExists_ShouldReturnTrue() {
        // Arrange
        when(productoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productoRepository).deleteById(1L);

        // Act
        boolean result = productoService.eliminarProducto(1L);

        // Assert
        assertTrue(result);
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarProducto_WhenProductNotExists_ShouldReturnFalse() {
        // Arrange
        when(productoRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = productoService.eliminarProducto(1L);

        // Assert
        assertFalse(result);
        verify(productoRepository, never()).deleteById(anyLong());
    }
}