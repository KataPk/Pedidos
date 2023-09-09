package com.example.pedidos.service;

import com.example.pedidos.dtos.PedidoRecordDTO;
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


    public List<PedidoRecordDTO> findAll(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedido -> new PedidoRecordDTO(pedido.getId(), pedido.getNomeCliente(), pedido.getDtRegistro(), pedido.getDtFechamento(),
                        pedido.getUser(), pedido.getMesa(), pedido.getStatusPedido()))
                .collect(Collectors.toList());

    }




}
