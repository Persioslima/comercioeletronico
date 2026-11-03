package br.com.portfolio.comercioeletronico.service;

import br.com.portfolio.comercioeletronico.entity.MarcaEntity;
import br.com.portfolio.comercioeletronico.exception.ConflitoNegocioException;
import br.com.portfolio.comercioeletronico.repository.MarcaRepository;
import br.com.portfolio.comercioeletronico.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    private  final MarcaRepository marcaRepository;
    private final ProdutoRepository produtoRepository;


    public MarcaService(MarcaRepository marcaRepository, ProdutoRepository produtoRepository) {
        this.marcaRepository = marcaRepository;
        this.produtoRepository = produtoRepository;
    }

    public MarcaEntity cadastrarMarca(MarcaEntity marca) {

        String nomeNormalizado = marca.getNome().trim();

        if (marcaRepository.existeMarcaComNome(nomeNormalizado)) {
            throw new ConflitoNegocioException("Marca existente!");
        }

        marca.setNome(nomeNormalizado);

        return marcaRepository.save(marca);
    }

    public List<MarcaEntity> listarMarcas() {
        return marcaRepository.findAll();
    }

    public Optional<MarcaEntity> buscarMarcaPorId(Long id) {
        return marcaRepository.findById(id);
    }

    public Optional<MarcaEntity> autualizarMarca(
            Long id,
            MarcaEntity marcaAtualizada) {


        return marcaRepository.findById(id)
                .map(marca ->  {
            String nomeAtualizado = marcaAtualizada.getNome().trim();

            if (marcaRepository.existeOutraMarcaComNome(
                    nomeAtualizado,
                    id)) {
                throw new ConflitoNegocioException("Marca existente!");
            }

            marca.setNome(nomeAtualizado);

            return marcaRepository.save(marca);

        });
    }

    public boolean excluirMarca(Long id) {

        if (!marcaRepository.existsById(id)) {
            return false;
        }

        if (produtoRepository.existsByMarca_Id(id)) {
            throw new ConflitoNegocioException(
                    "Não é possível excluir a marca porque existem produtos associados!"
            );
        }

        marcaRepository.deleteById(id);

        return true;
    }

}
