package br.com.portfolio.comercioeletronico.controller;

import br.com.portfolio.comercioeletronico.controller.dto.MarcaDto;
import br.com.portfolio.comercioeletronico.controller.dto.MarcaRespostaDto;
import br.com.portfolio.comercioeletronico.entity.MarcaEntity;
import br.com.portfolio.comercioeletronico.service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService marcaService;


    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @PostMapping
    public ResponseEntity<MarcaRespostaDto> cadastrarMarca(
            @Valid @RequestBody MarcaDto dto) {

        MarcaEntity marca = new MarcaEntity();
        marca.setNome(dto.nome());

        MarcaEntity marcaSalva = marcaService.cadastrarMarca(marca);

        MarcaRespostaDto resposta = new MarcaRespostaDto(
                marcaSalva.getId(),
                marcaSalva.getNome()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<MarcaRespostaDto>> listarMarcas() {

        List<MarcaRespostaDto> marcas = marcaService.listarMarcas()
                .stream()
                .map(marca -> new MarcaRespostaDto(
                        marca.getId(),
                        marca.getNome()
                ))
                .toList();
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaRespostaDto> buscaarMarcaPorId(
            @PathVariable Long id){

        return marcaService.buscarMarcaPorId(id)
                .map(marca -> new MarcaRespostaDto(
                        marca.getId(),
                        marca.getNome()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaRespostaDto> atualizarMarca(
            @PathVariable Long id,
            @Valid @RequestBody MarcaDto dto) {

                MarcaEntity marcaAtualizada = new MarcaEntity();
                marcaAtualizada.setNome(dto.nome());

                return marcaService.autualizarMarca(id, marcaAtualizada)
                        .map(marca -> new MarcaRespostaDto(
                                marca.getId(),
                                marca.getNome()
                        ))
                        .map(ResponseEntity::ok)
                        .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMarca(@PathVariable Long id) {

        boolean excluida = marcaService.excluirMarca(id);

        if (! excluida) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
