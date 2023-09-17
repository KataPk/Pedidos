package com.example.pedidos.service;

import com.example.pedidos.dtos.ItemPedidoRecordDto;
import com.example.pedidos.dtos.MesaRecordDto;
import com.example.pedidos.model.entity.ItemPedido;
import com.example.pedidos.model.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;


    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<ItemPedidoRecordDto> findItensByPedido(long pedidoId){
       List<ItemPedido> itens =  itemPedidoRepository.findAllByPedido(pedidoId);
        return itens.stream()
                .map(item -> new ItemPedidoRecordDto(item.getId(), item.getProduto(), item.getObservacao(), item.getPedido()))
                .collect(Collectors.toList());
    }





}
