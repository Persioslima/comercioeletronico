package br.com.portfolio.comercioeletronico.service;

import br.com.portfolio.comercioeletronico.entity.CategoriaEntity;
import br.com.portfolio.comercioeletronico.exception.ConflitoNegocioException;
import br.com.portfolio.comercioeletronico.repository.CategoriaRepository;
import br.com.portfolio.comercioeletronico.repository.ProdutoRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;


    public CategoriaService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;

    }

    public CategoriaEntity cadastrarCategoria(CategoriaEntity categoria) {

        String nomeNormalizado = categoria.getNome().trim();

        if (categoriaRepository.existeCategoriaComNome(nomeNormalizado)) {
            throw new ConflitoNegocioException("Categoria existente!");
        }

        categoria.setNome(nomeNormalizado);

        return categoriaRepository.save(categoria);
    }

    public List<CategoriaEntity> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<CategoriaEntity> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Optional<CategoriaEntity> atualizarCategoria(
            Long id,
            CategoriaEntity dadosAtualizados) {
        return categoriaRepository.findById(id)
                .map(categoria -> {

                    String nomeAtualizado =
                            (dadosAtualizados.getNome().trim());

                    if (categoriaRepository.existeOutraCategoriaComNome(
                            nomeAtualizado, id)) {
                        throw new ConflitoNegocioException("Categoria existente!");
                    }

                    categoria.setNome(nomeAtualizado);

                    return categoriaRepository.save(categoria);
                });
    }

    public boolean excluirCategoria(Long id) {
        if (! categoriaRepository.existsById(id)) {
            return false;
        }

        if (produtoRepository.existsByCategoria_Id(id)) {
            throw new ConflitoNegocioException(
                    "Não é possível excluir a categoria porque existem produtos associados!"
            );
        }

        categoriaRepository.deleteById(id);
        return true;
    }


}
