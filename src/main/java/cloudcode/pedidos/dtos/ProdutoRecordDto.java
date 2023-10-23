package cloudcode.pedidos.dtos;

import cloudcode.pedidos.model.entity.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ProdutoRecordDto(

        @NotBlank long id,
        @NotBlank String nome,
        @NotBlank String descricao,

        @NotNull String imagem,
        double valor,
        @NotBlank Categoria categoria,

        String statusProduto
) {

    public ProdutoRecordDto {
    }
}
