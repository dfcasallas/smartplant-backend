package com.Smartplants.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgregarPlantaInventarioCommand {
    private Long usuarioId;
    private Long plantaId;
    private String nombrePersonalizado;
}
