package com.example.pedidos.service;

import com.example.pedidos.dtos.PedidoRecordDTO;
import com.example.pedidos.dtos.PedidoSubTotalRecordDTO;
import com.example.pedidos.model.entity.Mesa;
import com.example.pedidos.model.entity.Pedido;
import com.example.pedidos.model.repository.MesaRepository;
import com.example.pedidos.model.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MesaRepository mesaRepository;

    @Autowired
    UserService userService;

    @Autowired
    ItemPedidoService itemPedidoService;


    public List<PedidoRecordDTO> findAll(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedido -> new PedidoRecordDTO(pedido.getId(), pedido.getNomeCliente(), pedido.getDtRegistro(), pedido.getDtFechamento(),
                        pedido.getUser(), pedido.getMesa(), pedido.getStatusPedido()))
                .collect(Collectors.toList());

    }
    public List<PedidoRecordDTO> findAbertos(){
        List<Pedido> pedidos = pedidoRepository.findByStatusPedido("ABERTO");
        return pedidos.stream()
                .map(pedido -> new PedidoRecordDTO(pedido.getId(), pedido.getNomeCliente(), pedido.getDtRegistro(), pedido.getDtFechamento(),
                        pedido.getUser(), pedido.getMesa(), pedido.getStatusPedido()))
                .collect(Collectors.toList());
    }

    public List<PedidoSubTotalRecordDTO> findPedidosSubtotal(List<PedidoRecordDTO> pedidos){

        for (PedidoRecordDTO pedido: pedidos) {
            long pedidoId = pedido.id();
            double subTotal = itemPedidoService.getSubTotal(pedidoId);
            

        }



    }



}
