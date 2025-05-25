package microservicio1.microservicio_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservicio1.microservicio_1.model.Inventario;
import microservicio1.microservicio_1.service.InventarioService;

@RestController
@RequestMapping ("/api/inventario")

public class InventarioController {
    


@Autowired
private InventarioService inventarioService;

@PostMapping
public Inventario crearInventario(@RequestBody Inventario inventario) {
    return inventarioService.crearInventario(inventario);
}

}
