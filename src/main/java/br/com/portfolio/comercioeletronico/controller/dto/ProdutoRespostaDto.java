package br.com.portfolio.comercioeletronico.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRespostaDto(
        Long id,
        String nome,
        BigDecimal preco,
        Long categoriaId,
        String categoriaNome,
        Long marcaId,
        String marcaNome,
        List<EtiquetaRespostaDto> etiquetas
) {
}
