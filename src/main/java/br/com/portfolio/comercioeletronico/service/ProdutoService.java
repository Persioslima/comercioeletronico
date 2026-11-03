package br.com.portfolio.comercioeletronico.service;

import br.com.portfolio.comercioeletronico.entity.CategoriaEntity;
import br.com.portfolio.comercioeletronico.entity.EtiquetaEntity;
import br.com.portfolio.comercioeletronico.entity.MarcaEntity;
import br.com.portfolio.comercioeletronico.entity.ProdutoEntity;
import br.com.portfolio.comercioeletronico.exception.RecursoNaoEncontradoException;
import br.com.portfolio.comercioeletronico.repository.CategoriaRepository;
import br.com.portfolio.comercioeletronico.repository.EtiquetaRepository;
import br.com.portfolio.comercioeletronico.repository.MarcaRepository;
import br.com.portfolio.comercioeletronico.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final EtiquetaRepository etiquetaRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository,
                          MarcaRepository marcaRepository, EtiquetaRepository etiquetaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
        this.etiquetaRepository = etiquetaRepository;
    }

    public ProdutoEntity cadastrarProduto(
            ProdutoEntity produto,
            Long categoriaId,
            Long marcaId,
            List<Long> etiquetasIds) {

        CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Categoria não encontrada!"));

        MarcaEntity marca = marcaRepository.findById(marcaId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Marca não encontrada!"));

        List<EtiquetaEntity> etiquetas = buscarEtiquetas(etiquetasIds);

        produto.setNome(produto.getNome().trim());
        produto.setCategoria(categoria);
        produto.setMarca(marca);
        produto.setEtiquetas(etiquetas);

        return produtoRepository.save(produto);
    }

    public Page<ProdutoEntity> listarProdutos(
            String nome,
            Long categoriaId,
            Long marcaId,
            Pageable pageable) {

        if (nome != null && nome.isBlank()) {
            nome = null;
        }

        if (nome != null) {
            nome = nome.trim();
        }

        return produtoRepository.buscarProdutosComFiltros(
                nome,
                categoriaId,
                marcaId,
                pageable
        );
    }

    public Optional<ProdutoEntity> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Optional<ProdutoEntity> atualizarProduto(
            Long id,
            ProdutoEntity produtoAtualizado,
            Long categoriaId,
            Long marcaId,
            List<Long> etiquetasIds) {

        List<EtiquetaEntity> etiquetas = buscarEtiquetas(etiquetasIds);

        return produtoRepository.findById(id)
                .map(produto -> {
                    CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                            .orElseThrow(() ->
                                    new RecursoNaoEncontradoException("Categoria não encontrada!"));

                    MarcaEntity marca = marcaRepository.findById(marcaId)
                            .orElseThrow(()->
                                    new RecursoNaoEncontradoException("Marca Não Encontrada!"));

                    produto.setNome(produtoAtualizado.getNome().trim());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setCategoria(categoria);
                    produto.setMarca(marca);
                    produto.setEtiquetas(etiquetas);


                    return produtoRepository.save(produto);
                });
    }

    public boolean excluirProduto(Long id) {

        if (! produtoRepository.existsById(id)) {

            return false;
        }

        produtoRepository.deleteById(id);

        return true;
    }

    private List<EtiquetaEntity> buscarEtiquetas(List<Long> etiquetasIds) {

        if (etiquetasIds == null || etiquetasIds.isEmpty()) {
            return List.of();
        }

        if (etiquetasIds.stream().anyMatch(id -> id == null)) {
            throw new RecursoNaoEncontradoException(
                    "Etiqueta não encontrada!"
            );
        }

        List<EtiquetaEntity> etiquetas =
                etiquetaRepository.findAllById(etiquetasIds);

        if (etiquetas.size() != etiquetasIds.stream().distinct().count()) {
            throw new RecursoNaoEncontradoException(
                    "Uma ou mais etiquetas não foram encontradas!"
            );
        }

        return etiquetas;
    }
}
