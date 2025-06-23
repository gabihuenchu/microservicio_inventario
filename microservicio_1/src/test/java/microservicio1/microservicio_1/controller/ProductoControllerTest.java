package microservicio1.microservicio_1.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservicio1.microservicio_1.model.Producto;
import microservicio1.microservicio_1.service.ProductoService;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private Producto producto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
        producto = new Producto("Producto 1", "Descripción", 10, 1000.0);
        producto.setId(1L);
    }

    @Test
    void listar_ShouldReturnAllProducts() throws Exception {
        // Arrange
        List<Producto> productos = Arrays.asList(producto);
        when(productoService.listarProductos()).thenReturn(productos);

        // Act & Assert
        mockMvc.perform(get("/api/productos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombre").value("Producto 1"));
    }

    @Test
    void crear_ShouldCreateProduct() throws Exception {
        // Arrange
        when(productoService.guardarProducto(any(Producto.class))).thenReturn(producto);

        // Act & Assert
        mockMvc.perform(post("/api/productos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(producto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Producto 1"));
    }

    @Test
    void obtener_ShouldReturnProduct() throws Exception {
        // Arrange
        when(productoService.obtenerProductoPorId(1L)).thenReturn(producto);

        // Act & Assert
        mockMvc.perform(get("/api/productos/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre").value("Producto 1"));
    }

    @Test
    void eliminar_WhenProductExists_ShouldReturnNoContent() throws Exception {
        // Arrange
        when(productoService.eliminarProducto(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/productos/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_WhenProductNotExists_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(productoService.eliminarProducto(1L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/productos/1"))
               .andExpect(status().isNotFound());
    }
}