package com.Smartplants.application.port.in;

import com.Smartplants.domain.model.PreferenciasUsuario;
import com.Smartplants.domain.model.ResultadoSugerencia;

public interface SugerenciaUseCase {
    ResultadoSugerencia sugerir(PreferenciasUsuario preferencias);
}
