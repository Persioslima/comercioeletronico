package br.com.portfolio.comercioeletronico.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record MarcaDto(

        @NotBlank(message = "Nome da marca é obrigatório")
        String nome
) {
}
