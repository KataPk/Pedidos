package com.example.pedidos.dtos;

import com.example.pedidos.model.entity.Contato;
import com.example.pedidos.model.entity.ERole;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record UserDetailsDto(

        @NotBlank
        String username,
        @NotBlank
        String password,

        Set<ERole> roles


) {
    public UserDetailsDto(String username, String password, Set<ERole> roles) {

        this.username = username;
        this.password = password;
        this.roles = roles;


    }


}
