package com.example.pedidos.dtos;

import jakarta.validation.constraints.NotBlank;


public record MesaRecordDto(

        @NotBlank  Integer numMesa,
        @NotBlank  String mStatus

) {

    public MesaRecordDto {
    }
}
