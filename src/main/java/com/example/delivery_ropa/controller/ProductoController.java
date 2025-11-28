package com.example.delivery_ropa.controller;

import com.example.delivery_ropa.model.Producto;
import com.example.delivery_ropa.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //Crear Producto
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto){
        Producto nuevoProducto = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    //Obtener todos los Productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    //Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtener productos por Categoría
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria){
        return ResponseEntity.ok(productoService.obtenerPorCategoria(categoria));
    }

    //Buscar productos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    //Actualizar Producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto producto){
        try {
            Producto productoActualizado = productoService.actualizar(id, producto);
            return ResponseEntity.ok(productoActualizado);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Eliminar Producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok("Producto eliminado exitosamente");
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
