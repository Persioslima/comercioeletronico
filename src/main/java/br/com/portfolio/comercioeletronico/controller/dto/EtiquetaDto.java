package br.com.portfolio.comercioeletronico.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record EtiquetaDto(

        @NotBlank(message = "O nome da etiqueta é obrigatório")
        String nome
) {
}
