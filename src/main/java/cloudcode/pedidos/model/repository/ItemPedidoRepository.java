package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.ItemPedido;
import cloudcode.pedidos.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {


    List<ItemPedido> findByPedidoOrderById(Pedido pedido);


}
