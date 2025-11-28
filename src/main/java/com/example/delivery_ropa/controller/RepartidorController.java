package com.example.delivery_ropa.controller;

import com.example.delivery_ropa.model.Repartidor;
import com.example.delivery_ropa.model.Repartidor.EstadoRepartidor;
import com.example.delivery_ropa.service.RepartidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    //Crear repartidor
    @PostMapping
    public ResponseEntity<Repartidor> crear(@RequestBody Repartidor repartidor){
        Repartidor nuevoRepartidor = repartidorService.crear(repartidor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRepartidor);
    }

    //Obtener todos los repartidores
    @GetMapping
    public ResponseEntity<List<Repartidor>> obtenerTodos() {
        return ResponseEntity.ok(repartidorService.obtenerTodos());
    }

    //Obtener repartidor por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return repartidorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtener repartidores disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Repartidor>> obtenerDisponibles() {
        return ResponseEntity.ok(repartidorService.obtenerDisponibles());
    }

    //Obtener repartidores por estado
    @GetMapping("/estado{estado}")
    public ResponseEntity<List<Repartidor>> obtenerPorEstado(@PathVariable EstadoRepartidor estado){
        return ResponseEntity.ok(repartidorService.obtenerPorEstado(estado));
    }

    //Asignar pedido a repartidor(con validación: solo un pedido a la vez)
    @PutMapping("/{repartidorId}/asignar/{pedidoId}")
    public ResponseEntity<?> asignarPedido(@PathVariable Long repartidorId, @PathVariable Long pedidoId){
        try {
            Repartidor repartidor = repartidorService.asignarPedido(repartidorId, pedidoId);
            return ResponseEntity.ok(repartidor);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Liberar repartidor(después de entregar)
    @PutMapping("/{repartidorId}/liberar")
    public ResponseEntity<?> liberarRepartidor(@PathVariable Long repartidorId){
        try {
            Repartidor repartidor = repartidorService.liberarRepartidor(repartidorId);
            return ResponseEntity.ok(repartidor);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Actualizar repartidor
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Repartidor repartidor) {
        try {
            Repartidor repartidorActualizado = repartidorService.actualizar(id, repartidor);
            return ResponseEntity.ok(repartidorActualizado);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Eliminar repartidor
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            repartidorService.eliminar(id);
            return ResponseEntity.ok("Repartidor eliminado exitosamente");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
