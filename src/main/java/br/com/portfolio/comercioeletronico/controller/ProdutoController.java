package br.com.portfolio.comercioeletronico.controller;

import br.com.portfolio.comercioeletronico.controller.dto.EtiquetaRespostaDto;
import br.com.portfolio.comercioeletronico.controller.dto.ProdutoDto;
import br.com.portfolio.comercioeletronico.controller.dto.ProdutoRespostaDto;
import br.com.portfolio.comercioeletronico.entity.ProdutoEntity;
import br.com.portfolio.comercioeletronico.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoRespostaDto> cadastrarProduto(
            @Valid @RequestBody ProdutoDto dto) {

        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());

        ProdutoEntity produtoSalvo = produtoService.cadastrarProduto(
                produto,
                dto.categoriaId(),
                dto.marcaId(),
                dto.etiquetasIds()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(converterParaResposta(produtoSalvo));
    }


    @GetMapping
    public ResponseEntity<Page<ProdutoRespostaDto>> listarProdutos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long marcaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProdutoRespostaDto> produtos = produtoService.listarProdutos(
                nome,
                categoriaId,
                marcaId,
                pageable
        ).map(this::converterParaResposta);

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRespostaDto> buscarProdutoPorId(
            @PathVariable Long id) {

        return produtoService.buscarProdutoPorId(id)
                .map(this::converterParaResposta)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRespostaDto> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDto dto) {

        ProdutoEntity produtoAtualizado = new ProdutoEntity();
        produtoAtualizado.setNome(dto.nome());
        produtoAtualizado.setPreco(dto.preco());

        return produtoService.atualizarProduto(
                        id,
                        produtoAtualizado,
                        dto.categoriaId(),
                        dto.marcaId(),
                        dto.etiquetasIds()
                )
                .map(this::converterParaResposta)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {

        boolean excluido = produtoService.excluirProduto(id);

        if (!excluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    private ProdutoRespostaDto converterParaResposta(ProdutoEntity produto) {

        List<EtiquetaRespostaDto> etiquetas = produto.getEtiquetas()
                .stream()
                .map(etiqueta -> new EtiquetaRespostaDto(
                        etiqueta.getId(),
                        etiqueta.getNome()
                ))
                .toList();

        return new ProdutoRespostaDto(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                produto.getMarca().getId(),
                produto.getMarca().getNome(),
                etiquetas
        );
    }
}