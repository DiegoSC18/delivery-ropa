package com.example.delivery_ropa.controller;

import com.example.delivery_ropa.model.DetallePedido;
import com.example.delivery_ropa.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    //Obtener todos los detalles
    @GetMapping
    public ResponseEntity<List<DetallePedido>> obtenerTodos() {
        return ResponseEntity.ok(detallePedidoService.obtenerTodos());
    }

    //Obtener detalle por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return detallePedidoService.obtenerporId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtener detalles de un pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<DetallePedido>> obtenerPorPedido(@PathVariable Long pedidoId){
        return ResponseEntity.ok(detallePedidoService.obtenerPorPedido(pedidoId));
    }

    //Obtener detalles de un producto
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<DetallePedido>> obtenerPorProducto(@PathVariable Long prodcutoId){
        return ResponseEntity.ok(detallePedidoService.obtenerPorProducto(prodcutoId));
    }

    //Eliminar detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            detallePedidoService.eliminar(id);
            return ResponseEntity.ok("Detalle eliminado exitosamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
