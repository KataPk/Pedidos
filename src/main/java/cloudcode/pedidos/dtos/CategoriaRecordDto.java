package cloudcode.pedidos.dtos;

import jakarta.validation.constraints.NotNull;

public record CategoriaRecordDto(

        @NotNull long id,
        @NotNull String nome,
        @NotNull String imagem,
        @NotNull String statusCategoria


) {
    public CategoriaRecordDto {
    }
}
