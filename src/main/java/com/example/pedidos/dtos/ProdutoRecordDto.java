package com.example.pedidos.dtos;

import com.example.pedidos.model.entity.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ProdutoRecordDto(

        @NotBlank  String nome,
        @NotBlank  String descricao,

        @NotNull  String imagem,
        double valor,
        @NotBlank Categoria categoria
        ) {

    public ProdutoRecordDto {
    }
}
