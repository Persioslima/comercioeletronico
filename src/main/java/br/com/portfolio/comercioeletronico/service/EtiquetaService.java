package br.com.portfolio.comercioeletronico.service;

import br.com.portfolio.comercioeletronico.entity.EtiquetaEntity;
import br.com.portfolio.comercioeletronico.exception.ConflitoNegocioException;
import br.com.portfolio.comercioeletronico.repository.EtiquetaRepository;
import br.com.portfolio.comercioeletronico.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;
    private final ProdutoRepository produtoRepository;

    public EtiquetaService(EtiquetaRepository etiquetaRepository, ProdutoRepository produtoRepository) {
        this.etiquetaRepository = etiquetaRepository;
        this.produtoRepository = produtoRepository;
    }

    public EtiquetaEntity cadastrarEtiqueta(EtiquetaEntity etiqueta) {

        String nomeNormalizado = etiqueta.getNome().trim();

        if (etiquetaRepository.existeEtiquetaComNome(nomeNormalizado)) {
            throw new ConflitoNegocioException("Etiqueta existente!");
        }

        etiqueta.setNome(nomeNormalizado);

        return etiquetaRepository.save(etiqueta);
    }

    public List<EtiquetaEntity> listarEtiquetas() {
        return etiquetaRepository.findAll();
    }

    public Optional<EtiquetaEntity> buscarEtiquetaPorId(Long id) {
        return etiquetaRepository.findById(id);
    }

    public Optional<EtiquetaEntity> atualizarEtiqueta(
            Long id,
            EtiquetaEntity etiquetaAtualizada) {

        return etiquetaRepository.findById(id)
                .map(etiqueta -> {

                    String nomeAtualizado = etiquetaAtualizada.getNome().trim();

                    if (etiquetaRepository.existeOutraEtiquetaComNome(
                            nomeAtualizado,
                            id)) {
                        throw new ConflitoNegocioException("Etiqueta existente!");
                    }

                    etiqueta.setNome(nomeAtualizado);

                    return etiquetaRepository.save(etiqueta);
                });

    }

    public boolean excluirEtiqueta(Long id) {

        if (!etiquetaRepository.existsById(id)) {
            return false;
        }

        if (produtoRepository.existsByEtiquetas_Id(id)) {
            throw new ConflitoNegocioException(
                    "Não é possível excluir a etiqueta porque existem produtos associados!"
            );
        }

        etiquetaRepository.deleteById(id);

        return true;

    }
}
