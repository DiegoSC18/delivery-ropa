package com.example.delivery_ropa.repository;

import com.example.delivery_ropa.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //Metodo perzonalizado: Busca por categoría
    List<Producto> findByCategoria(String categoria);

    //Buscar productos por Nombre(Que contenga)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    //Buscar Productos con stock mayor a X
    List<Producto> findByStockGreaterThan(Integer stock);

}
