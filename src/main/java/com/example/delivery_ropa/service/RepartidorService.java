package com.example.delivery_ropa.service;

import com.example.delivery_ropa.model.Pedido;
import com.example.delivery_ropa.model.Pedido.EstadoPedido;
import com.example.delivery_ropa.model.Repartidor;
import com.example.delivery_ropa.model.Repartidor.EstadoRepartidor;
import com.example.delivery_ropa.repository.PedidoRepository;
import com.example.delivery_ropa.repository.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepartidorService {

    @Autowired
    private RepartidorRepository repartidorRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    //REGLA DE NEGOCIO 5: Solo un pedido a la vez
    public Repartidor asignarPedido(Long repartidorId, Long pedidoId) {

        //Buscamos repartidor
        Repartidor repartidor = repartidorRepository.findById(repartidorId)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con id: " + repartidorId));

        //Verificar que el repartidor esté DISPONIBLE
        if (repartidor.getEstado() != EstadoRepartidor.DISPONIBLE) {
            throw new RuntimeException(
                    "El repartidor " + repartidor.getNombre() + " " + repartidor.getApellido() +
                            " no está disponible. Estado actual: " + repartidor.getEstado()
            );
        }

        //Buscar pedido
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + pedidoId));

        //Asignar pedido al repartidor
        repartidor.setPedido(pedido);
        repartidor.setEstado(EstadoRepartidor.OCUPADO);

        //Cambiar estado del pedido a EN_CAMINO
        pedido.setEstado(EstadoPedido.EN_CAMINO);
        pedidoRepository.save(pedido);

        return repartidorRepository.save(repartidor);
    }

    //Liberar repartidor después de entregar
    public Repartidor liberarRepartidor(Long repartidorId){
        Repartidor repartidor = repartidorRepository.findById(repartidorId)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con id: " +repartidorId));

        //Liberamos el Pedido
        if (repartidor.getPedido() !=null) {
            Pedido pedido = repartidor.getPedido();
            pedido.setEstado(EstadoPedido.ENTREGADO);
            pedidoRepository.save(pedido);
        }

        repartidor.setPedido(null);
        repartidor.setEstado(EstadoRepartidor.DISPONIBLE);

        return repartidorRepository.save(repartidor);
    }

    //Métodos CRUD básicos
    public Repartidor crear(Repartidor repartidor) {
        return repartidorRepository.save(repartidor);
    }

    public List<Repartidor> obtenerTodos() {
        return repartidorRepository.findAll();
    }

    public Optional<Repartidor> obtenerPorId(Long id) {
        return repartidorRepository.findById(id);
    }

    public List<Repartidor> obtenerDisponibles() {
        return repartidorRepository.findByEstado(EstadoRepartidor.DISPONIBLE);
    }

    public List<Repartidor> obtenerPorEstado(EstadoRepartidor estado){
        return repartidorRepository.findByEstado(estado);
    }

    public Repartidor actualizar(Long id, Repartidor repartidorActualizado) {
        return repartidorRepository.findById(id)
                .map(repartidor -> {
                    repartidor.setNombre(repartidorActualizado.getNombre());
                    repartidor.setApellido(repartidorActualizado.getApellido());
                    repartidor.setTelefono(repartidorActualizado.getTelefono());
                    repartidor.setVehiculo(repartidorActualizado.getVehiculo());
                    repartidor.setPlaca(repartidorActualizado.getPlaca());
                    return repartidorRepository.save(repartidor);
                })
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con id: " +id));
    }

    public void eliminar(Long id) {
        repartidorRepository.deleteById(id);
    }
}
