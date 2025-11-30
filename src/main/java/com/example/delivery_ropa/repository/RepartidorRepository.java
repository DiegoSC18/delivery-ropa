package com.example.delivery_ropa.repository;

import com.example.delivery_ropa.model.Repartidor;
import com.example.delivery_ropa.model.Repartidor.EstadoRepartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {

    //Metodo personalizado: Buscar repartidores por estado
    List<Repartidor> findByEstado(EstadoRepartidor estado);

    //Buscar repartidores disponibles
    List<Repartidor> findByEstadoAndPedidoIsNull(EstadoRepartidor estado);

    boolean existsByPlaca(String placa);


}
