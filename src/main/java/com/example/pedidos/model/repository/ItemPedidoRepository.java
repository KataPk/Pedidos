package com.example.pedidos.model.repository;

import com.example.pedidos.model.entity.ItemPedido;
import com.example.pedidos.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {


        List<ItemPedido> findByPedido(Pedido pedido);


}
