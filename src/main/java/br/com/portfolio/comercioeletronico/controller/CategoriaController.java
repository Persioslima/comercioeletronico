package br.com.portfolio.comercioeletronico.controller;

import br.com.portfolio.comercioeletronico.controller.dto.CategoriaDto;
import br.com.portfolio.comercioeletronico.controller.dto.CategoriaRespostaDto;
import br.com.portfolio.comercioeletronico.entity.CategoriaEntity;
import br.com.portfolio.comercioeletronico.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaRespostaDto> cadastrarCategoria(
            @Valid @RequestBody CategoriaDto dto) {

        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNome(dto.nome());

        CategoriaEntity categoriaSalva =
                categoriaService.cadastrarCategoria(categoria);

        CategoriaRespostaDto resposta = new CategoriaRespostaDto(
                categoriaSalva.getId(),
                categoriaSalva.getNome()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaRespostaDto>> listarCategorias() {

        List<CategoriaRespostaDto> categorias = categoriaService.listarCategorias()
                .stream()
                .map(categoria -> new CategoriaRespostaDto(
                        categoria.getId(),
                        categoria.getNome()
                ))
                .toList();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRespostaDto> buscarCategoriaPorId(
            @PathVariable Long id) {

        return categoriaService.buscarCategoriaPorId(id)
                .map(categoria -> new CategoriaRespostaDto(
                        categoria.getId(),
                        categoria.getNome()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRespostaDto> atualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDto dto) {

        CategoriaEntity dadosAtualizados = new CategoriaEntity();
        dadosAtualizados.setNome(dto.nome());

        return categoriaService.atualizarCategoria(id, dadosAtualizados)
                .map(categoria -> new CategoriaRespostaDto(
                        categoria.getId(),
                        categoria.getNome()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {

        boolean excluida = categoriaService.excluirCategoria(id);

        if (! excluida) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}
