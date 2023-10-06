package com.example.pedidos.dtos;

import com.example.pedidos.model.entity.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Set;


public record CreateUserRecordDto(

        @NotBlank  String nome,
        @NotBlank  String cpf,
        @NotBlank String rg,
        @NotBlank  @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNasc,
        @NotBlank String logradouro,
        @NotBlank String numResid,
        @NotBlank String cep,

        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String uf,
        String complemento,
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String senha,

       Set<ERole> roles

) {

    public CreateUserRecordDto {
    }
}
