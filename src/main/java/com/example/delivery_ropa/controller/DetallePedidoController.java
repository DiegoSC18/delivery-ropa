package com.example.delivery_ropa.controller;

import com.example.delivery_ropa.model.DetallePedido;
import com.example.delivery_ropa.model.Pedido;
import com.example.delivery_ropa.model.Producto;
import com.example.delivery_ropa.service.DetallePedidoService;
import com.example.delivery_ropa.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> request){
        try {
            Long pedidoId = Long.valueOf(request.get("pedidoId").toString());
            Long productoId = Long.valueOf(request.get("productoId").toString());
            Integer cantidad = Integer.valueOf(request.get("cantidad").toString());

            Pedido pedido = new Pedido();
            pedido.setId(pedidoId);

            Producto producto = productoService.obtenerPorId(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetallePedido detalle = detallePedidoService.crearDetalle(pedido, producto, cantidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalle);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
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
