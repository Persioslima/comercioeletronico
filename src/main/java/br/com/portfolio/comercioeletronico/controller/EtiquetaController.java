package br.com.portfolio.comercioeletronico.controller;

import br.com.portfolio.comercioeletronico.controller.dto.EtiquetaDto;
import br.com.portfolio.comercioeletronico.controller.dto.EtiquetaRespostaDto;
import br.com.portfolio.comercioeletronico.entity.EtiquetaEntity;
import br.com.portfolio.comercioeletronico.service.EtiquetaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/etiquetas")
public class EtiquetaController {

    private final EtiquetaService etiquetaService;

    public EtiquetaController(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    @PostMapping
    public ResponseEntity<EtiquetaRespostaDto> cadastrarEtiqueta(
            @Valid @RequestBody EtiquetaDto dados) {

        EtiquetaEntity etiqueta = new EtiquetaEntity();
        etiqueta.setNome(dados.nome());

        EtiquetaEntity etiquetaSalva =
                etiquetaService.cadastrarEtiqueta(etiqueta);

        EtiquetaRespostaDto resposta = converterParaDto(etiquetaSalva);

        return ResponseEntity
                .created(URI.create("/etiquetas/" + etiquetaSalva.getId()))
                .body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<EtiquetaRespostaDto>> listarEtiquetas() {

        List<EtiquetaRespostaDto> etiquetas = etiquetaService
                .listarEtiquetas()
                .stream()
                .map(this::converterParaDto)
                .toList();

        return ResponseEntity.ok(etiquetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtiquetaRespostaDto> buscarEtiquetaPorId(
            @PathVariable Long id) {

        return etiquetaService.buscarEtiquetaPorId(id)
                .map(etiqueta -> ResponseEntity.ok(converterParaDto(etiqueta)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtiquetaRespostaDto> atualizarEtiqueta(
            @PathVariable Long id,
            @Valid @RequestBody EtiquetaDto dados) {

        EtiquetaEntity etiquetaAtualizada = new EtiquetaEntity();
        etiquetaAtualizada.setNome(dados.nome());

        return etiquetaService.atualizarEtiqueta(id, etiquetaAtualizada)
                .map(etiqueta -> ResponseEntity.ok(converterParaDto(etiqueta)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEtiqueta(@PathVariable Long id) {

        if (!etiquetaService.excluirEtiqueta(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    private EtiquetaRespostaDto converterParaDto(EtiquetaEntity etiqueta) {
        return new EtiquetaRespostaDto(
                etiqueta.getId(),
                etiqueta.getNome()
        );
    }
}