package com.example.delivery_ropa.service;

import com.example.delivery_ropa.model.Producto;
import com.example.delivery_ropa.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //REGLA DE NEGOCIO 2: Validar y reducir stock

    public void validarYReducirStock(Long productoId, Integer cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));

        //Validar que haya stock suficiente
        if (producto.getStock() < cantidad){
            throw new RuntimeException(
                    "Stock insuficiente para el producto con id: " + productoId +
                    ". Stock actual: " + producto.getStock() +
                    ", cantidad solicitada: " +cantidad
            );
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    //Métodos CRUD básicos
    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescripcion(productoActualizado.getDescripcion());
                    producto.setCategoria(productoActualizado.getCategoria());
                    producto.setTalla(productoActualizado.getTalla());
                    producto.setColor(productoActualizado.getColor());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStock(productoActualizado.getStock());
                    producto.setMarca(productoActualizado.getMarca());
                    return productoRepository.save(producto);

                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " +id));
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
