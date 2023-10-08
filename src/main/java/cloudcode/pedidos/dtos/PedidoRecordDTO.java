package cloudcode.pedidos.dtos;


import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.entity.User;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PedidoRecordDTO(

        @NotBlank long id,
        @NotBlank String nomeCliente,

        @NotBlank LocalDateTime dtRegisto,

        LocalDateTime dtFechamento,

        @NotBlank User funcionario,
        @NotBlank Mesa mesa,

        @NotBlank String statusPedido


) {

}
