package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PedidoRepository extends JpaRepository<Pedido, Long> {


    Pedido findByMesa(Mesa mesa);

    List<Pedido> findByStatusPedido(String statusPedido);

}
