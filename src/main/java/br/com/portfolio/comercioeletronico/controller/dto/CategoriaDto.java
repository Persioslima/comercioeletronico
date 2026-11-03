package br.com.portfolio.comercioeletronico.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDto(
        @NotBlank(message = "O nome da categoria é obrigatório")
        String nome ) {
}
