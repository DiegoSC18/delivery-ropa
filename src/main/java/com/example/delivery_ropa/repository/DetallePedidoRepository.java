package com.example.delivery_ropa.repository;

import com.example.delivery_ropa.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    //Metodo personalizado: Buscar detalles de un pedido específico
    List<DetallePedido> findByPedidoId(Long pedidoId);

    //Buscar detalles que contengan un producto especifico
    List<DetallePedido> findByProductoId(Long productoId);
}
