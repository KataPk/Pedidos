package cloudcode.pedidos.dtos;

import jakarta.validation.constraints.NotNull;


public record MesaRecordDto(

        @NotNull long id,
        @NotNull Integer numMesa,
        @NotNull String mStatus

) {

    public MesaRecordDto {
    }
}
