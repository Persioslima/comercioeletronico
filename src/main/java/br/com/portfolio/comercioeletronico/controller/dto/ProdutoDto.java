package br.com.portfolio.comercioeletronico.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoDto(
        @NotBlank(message = "O nome do produto é obrigatório")
        String nome,

        @NotNull(message = "O preço é obrigatório!")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero!")
        BigDecimal preco,

        @NotNull(message = "A categoria é obrigatória!")
        Long categoriaId,

        @NotNull(message = "A marca é obrigatória!")
        Long marcaId,

        List<Long> etiquetasIds
) {
}
