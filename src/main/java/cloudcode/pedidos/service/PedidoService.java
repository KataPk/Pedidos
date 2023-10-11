package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.PedidoRecordDTO;
import cloudcode.pedidos.dtos.PedidoSubTotalRecordDTO;
import cloudcode.pedidos.model.entity.Pedido;
import cloudcode.pedidos.model.repository.MesaRepository;
import cloudcode.pedidos.model.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MesaRepository mesaRepository;

    @Autowired
    UserService userService;

    @Autowired
    ItemPedidoService itemPedidoService;

    public List<PedidoRecordDTO> findAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedido -> new PedidoRecordDTO(pedido.getId(), pedido.getNomeCliente(), pedido.getDtRegistro(), pedido.getDtFechamento(),
                        pedido.getUser(), pedido.getMesa(), pedido.getStatusPedido()))
                .collect(Collectors.toList());

    }

    public List<PedidoRecordDTO> findAbertos() {
        List<Pedido> pedidos = pedidoRepository.findByStatusPedido("ABERTO");
        return pedidos.stream()
                .map(pedido -> new PedidoRecordDTO(pedido.getId(), pedido.getNomeCliente(), pedido.getDtRegistro(), pedido.getDtFechamento(),
                        pedido.getUser(), pedido.getMesa(), pedido.getStatusPedido()))
                .collect(Collectors.toList());
    }

    public List<PedidoSubTotalRecordDTO> findPedidosAbertosWithSubtotal() {

        List<Pedido> pedidos = pedidoRepository.findByStatusPedido("ABERTO");


        return pedidos.stream().map(pedido -> new PedidoSubTotalRecordDTO(pedido.getId(), pedido.getNomeCliente(),
                        pedido.getDtRegistro(), pedido.getDtFechamento(), pedido.getUser(), pedido.getMesa(),
                        pedido.getStatusPedido(), itemPedidoService.getSubTotal(pedido.getId())))
                .collect(Collectors.toList());


    }


}




