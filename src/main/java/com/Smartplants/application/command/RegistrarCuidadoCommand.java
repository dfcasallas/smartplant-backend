package com.Smartplants.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarCuidadoCommand {
    private Long inventarioId;
    private String tipoCuidado;
    private String observacion;
}
