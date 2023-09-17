package com.example.pedidos.dtos;

import com.example.pedidos.model.entity.Pedido;
import com.example.pedidos.model.entity.Produto;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRecordDto(

        @NotNull long id,
        @NotNull Produto produto,

        @NotNull int quant,
        String observacao,
        @NotNull Pedido pedido

        ) {




}
