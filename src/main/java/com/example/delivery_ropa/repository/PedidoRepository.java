package com.example.delivery_ropa.repository;

import com.example.delivery_ropa.model.Pedido;
import com.example.delivery_ropa.model.Pedido.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    //Metodo personalizado: Buscar pedidos de un cliente
    List<Pedido> findByClienteId(Long clienteId);

    //Buscar pedidos por Estado
    List<Pedido> findByEstado(EstadoPedido estado);

    //Buscar pedidos de un cliente con cierto estado
    List<Pedido> findByClienteIdAndEstado(Long clienteId, EstadoPedido estado);
}
