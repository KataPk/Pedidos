package cloudcode.pedidos.dtos;


import cloudcode.pedidos.model.entity.Pedido;
import cloudcode.pedidos.model.entity.Produto;
import jakarta.validation.constraints.NotNull;

public record ItemSubTotalRecordDto(

        @NotNull long id,
        @NotNull Produto produto,

        @NotNull int quant,
        String observacao,
        @NotNull Pedido pedido,

        double subTotal

) {


}
