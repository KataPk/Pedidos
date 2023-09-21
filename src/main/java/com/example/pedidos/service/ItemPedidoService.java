package com.example.pedidos.service;

import com.example.pedidos.dtos.ItemPedidoRecordDto;
import com.example.pedidos.dtos.ItemSubTotalRecordDto;
import com.example.pedidos.dtos.MesaRecordDto;
import com.example.pedidos.model.entity.ItemPedido;
import com.example.pedidos.model.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;


    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<ItemPedidoRecordDto> findAllByPedido(long pedidoId){
       List<ItemPedido> itens =  itemPedidoRepository.findAllById(Collections.singleton(pedidoId));
        return itens.stream()
                .map(item -> new ItemPedidoRecordDto(item.getId(), item.getProduto(), item.getQuantProduto(), item.getObservacao(), item.getPedido()))
                .collect(Collectors.toList());
    }

    public List<ItemSubTotalRecordDto> findPedidoSubTotal(long pedidoId){

        List<ItemPedido> itens =  itemPedidoRepository.findAllById(Collections.singleton(pedidoId));

        double subtotal = getSubTotal(pedidoId);

        return itens.stream()
                .map(item -> new ItemSubTotalRecordDto(item.getId(), item.getProduto(), item.getQuantProduto(), item.getObservacao(), item.getPedido(), subtotal))
                .collect(Collectors.toList());
    }


    public double getSubTotal(long pedidoId){
        double subTotal = 0.0;

        List<ItemPedido> itens =  itemPedidoRepository.findAllById(Collections.singleton(pedidoId));
        for (int i = 0; i < itens.size(); i++ ){

            int quant = itens.get(i).getQuantProduto();
            double productValue = itens.get(i).getProduto().getValor();
            double valorItem = quant*productValue;

            subTotal += valorItem;
        }


        return subTotal;
    }



}
