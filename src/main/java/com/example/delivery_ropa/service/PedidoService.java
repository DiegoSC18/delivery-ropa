package com.example.delivery_ropa.service;

import com.example.delivery_ropa.model.Cliente;
import com.example.delivery_ropa.model.DetallePedido;
import com.example.delivery_ropa.model.Pedido;
import com.example.delivery_ropa.model.Pedido.EstadoPedido;
import com.example.delivery_ropa.repository.ClienteRepository;
import com.example.delivery_ropa.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    //REGLA DE NEGOCIO 3: CALCULAR TOTAL AUTOMÁTICAMENTE
    public BigDecimal calcularTotal(Pedido pedido){
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return pedido.getDetalles().stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Pedido crarPedido(Pedido pedido) {

        //Buscar el cliente completo por ID
        if (pedido.getCliente() !=null && pedido.getCliente().getId() != null){
            Cliente clienteCompleto = clienteRepository.findById(pedido.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " +pedido.getCliente().getId()));
            pedido.setCliente(clienteCompleto);
        }
        //Calculamos y asignamos el total Automáticamente
        BigDecimal total = calcularTotal(pedido);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> obtenerPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> obtenerPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public Pedido cambiarEstado(Long id, EstadoPedido nuevoEstado) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setEstado(nuevoEstado);
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    public void eliminar(Long id){
        pedidoRepository.deleteById(id);
    }
}
