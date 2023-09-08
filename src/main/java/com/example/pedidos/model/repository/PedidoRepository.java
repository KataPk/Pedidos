package com.example.pedidos.model.repository;

import com.example.pedidos.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PedidoRepository extends JpaRepository<Pedido, Long> {


}
