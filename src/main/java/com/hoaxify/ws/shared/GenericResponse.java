package com.hoaxify.ws.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //argüman(parametre) alan bir const. üretilir.
public class GenericResponse {

    private String message;
}
