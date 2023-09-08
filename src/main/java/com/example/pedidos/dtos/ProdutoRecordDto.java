package com.example.pedidos.dtos;

import com.example.pedidos.model.entity.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record ProdutoRecordDto(

        @NotBlank  String nome,
        @NotBlank  String descricao,

        @NotNull  String imagem,
        @NotBlank BigDecimal valor,
        @NotBlank Categoria categoria
        ) {

    public ProdutoRecordDto {
    }
}
