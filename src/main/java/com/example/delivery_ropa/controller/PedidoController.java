package com.example.delivery_ropa.controller;

import com.example.delivery_ropa.model.Pedido;
import com.example.delivery_ropa.model.Pedido.EstadoPedido;
import com.example.delivery_ropa.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    //Crear pedido(con cálculo automático de total)
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido){
        Pedido nuevoPedido = pedidoService.crarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    //Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos(){
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    //Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtener pedidos por Cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPorCliente(@PathVariable Long clienteId){
        return ResponseEntity.ok(pedidoService.obtenerPorCliente(clienteId));
    }

    //Obtener pedidos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPorEstado(@PathVariable  EstadoPedido estado){
        return ResponseEntity.ok(pedidoService.obtenerPorEstado(estado));
    }

    //Cambiar estado del pedido
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        try{
            Pedido pedidoActualizado = pedidoService.cambiarEstado(id, estado);
            return ResponseEntity.ok(pedidoActualizado);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Eliminar Pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            pedidoService.eliminar(id);
            return ResponseEntity.ok("Pedido eliminado exitosamente");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
