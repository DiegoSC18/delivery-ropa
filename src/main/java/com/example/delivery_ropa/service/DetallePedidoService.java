package com.example.delivery_ropa.service;

import com.example.delivery_ropa.model.DetallePedido;
import com.example.delivery_ropa.model.Pedido;
import com.example.delivery_ropa.model.Producto;
import com.example.delivery_ropa.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoService productoService;

    //REGLA DE NEGOCIO 4: PRECIO AL MOMENTO DE COMPRA
    public DetallePedido crearDetalle(Pedido pedido, Producto producto, Integer cantidad) {

        //Validar y reducir stock
        productoService.validarYReducirStock(producto.getId(), cantidad);

        //Guardar el precio ACTUAL del producto(Precio al momento de la compra)
        BigDecimal precioActual = producto.getPrecio();

        //Creamos el detalle con el precio actual
        DetallePedido detalle = new DetallePedido(pedido, producto, cantidad, precioActual);
        return detallePedidoRepository.save(detalle);
    }

    //Métodos CRUD básicos
    public List<DetallePedido> obtenerTodos() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> obtenerporId(Long id) {
        return detallePedidoRepository.findById(id);
    }

    public List<DetallePedido> obtenerPorPedido(Long pedidoId) {
        return detallePedidoRepository.findByPedidoId(pedidoId);
    }

    public List<DetallePedido> obtenerPorProducto(Long productoId) {
        return detallePedidoRepository.findByProductoId(productoId);
    }

    public void eliminar(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}
