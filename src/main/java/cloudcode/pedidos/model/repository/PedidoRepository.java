package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PedidoRepository extends JpaRepository<Pedido, Long> {


    @Query(value = "SELECT * FROM pedidosview where pedidosview.mesa = :mesa", nativeQuery = true)
    Pedido findByMesa(@Param("mesa") Mesa mesa);

    @Query(value = "SELECT * FROM pedidosview where pedidosview.mesa = :mesa AND pedidosview.statuspedido = :status", nativeQuery = true)
    Pedido findByMesaAndStatusPedido(@Param("mesa") Mesa mesa, @Param("status") String status);

    @Query(value = "SELECT * FROM pedidosview where pedidosview.statuspedido = :status", nativeQuery = true)
    List<Pedido> findByStatusPedido(@Param("status") String statusPedido);

    @Query(value = "REFRESH MATERIALIZED VIEW pedidosview", nativeQuery = true)
    void updatePedidosView();
}
