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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private MarcaRepository marcaRepository;
    @Mock
    private EtiquetaRepository etiquetaRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarFalseQuandoProdutoNaoExiste() {

        Long produtoId = 999L;

        when(produtoRepository.existsById(produtoId))
                .thenReturn(false);

        boolean resultado = produtoService.excluirProduto(produtoId);

        assertFalse(resultado);
    }

    @Test
    void deveExcluirProdutoQuandoExiste() {
        Long produtoId = 1L;

        when(produtoRepository.existsById(produtoId)).thenReturn(true);

        boolean resultado = produtoService.excluirProduto(produtoId);

        assertTrue(resultado);
        verify(produtoRepository).deleteById(produtoId);
    }

    @Test
    void deveRetornarVazioQuandoProdutoNaoExiste() {
        Long produtoId = 999L;

        when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.empty());

        Optional<ProdutoEntity> resultado =
                produtoService.buscarProdutoPorId(produtoId);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveRetornarProdutoQuandoExiste() {
        Long produtoId = 1L;

        ProdutoEntity produto = new ProdutoEntity();

        when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.of(produto));

        Optional<ProdutoEntity> resultado =
                produtoService.buscarProdutoPorId(produtoId);

        assertTrue(resultado.isPresent());
        assertEquals(produto, resultado.get());
    }

    @Test
    void deveListarProdutosSemFiltros() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProdutoEntity> pagina =
                new PageImpl<>(List.of(new ProdutoEntity()));

        when(produtoRepository.buscarProdutosComFiltros(
                null, null, null, pageable
        )).thenReturn(pagina);

        Page<ProdutoEntity> resultado =
                produtoService.listarProdutos(
                        null, null, null, pageable
                );

        assertEquals(1, resultado.getTotalElements());

        verify(produtoRepository).buscarProdutosComFiltros(
                null, null, null, pageable
        );
    }

    @Test
    void deveListarProdutosFiltrandoPorNome() {
        String nome = "Notebook";
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProdutoEntity> pagina =
                new PageImpl<>(List.of(new ProdutoEntity()));

        when(produtoRepository.buscarProdutosComFiltros(
                nome, null, null, pageable
        )).thenReturn(pagina);

        Page<ProdutoEntity> resultado =
                produtoService.listarProdutos(
                        "  Notebook  ", null, null, pageable
                );

        assertEquals(1, resultado.getTotalElements());

        verify(produtoRepository).buscarProdutosComFiltros(
                "Notebook", null, null, pageable
        );
    }

    @Test
    void deveListarProdutosFiltrandoPorCategoria() {
        Long categoriaId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProdutoEntity> pagina =
                new PageImpl<>(List.of(new ProdutoEntity()));

        when(produtoRepository.buscarProdutosComFiltros(
                null, categoriaId, null, pageable
        )).thenReturn(pagina);

        Page<ProdutoEntity> resultado =
                produtoService.listarProdutos(
                        null, categoriaId, null, pageable
                );

        assertEquals(1, resultado.getTotalElements());

        verify(produtoRepository).buscarProdutosComFiltros(
                null, categoriaId, null, pageable
        );
    }

    @Test
    void deveListarProdutosFiltrandoPorMarca() {
        Long marcaId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProdutoEntity> pagina =
                new PageImpl<>(List.of(new ProdutoEntity()));

        when(produtoRepository.buscarProdutosComFiltros(
                null, null, marcaId, pageable
        )).thenReturn(pagina);

        Page<ProdutoEntity> resultado =
                produtoService.listarProdutos(
                        null, null, marcaId, pageable
                );

        assertEquals(1, resultado.getTotalElements());

        verify(produtoRepository).buscarProdutosComFiltros(
                null, null, marcaId, pageable
        );
    }

    @Test
    void deveListarProdutosComFiltrosCombinados() {
        String nome = "Notebook";
        Long categoriaId = 1L;
        Long marcaId = 2L;

        Pageable pageable = PageRequest.of(0, 10);

        Page<ProdutoEntity> pagina =
                new PageImpl<>(List.of(new ProdutoEntity()));

        when(produtoRepository.buscarProdutosComFiltros(
                nome, categoriaId, marcaId, pageable
        )).thenReturn(pagina);

        Page<ProdutoEntity> resultado =
                produtoService.listarProdutos(
                        "  Notebook  ", categoriaId, marcaId, pageable
                );

        assertEquals(1, resultado.getTotalElements());

        verify(produtoRepository).buscarProdutosComFiltros(
                "Notebook", categoriaId, marcaId, pageable
        );
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        ProdutoEntity produto = new ProdutoEntity();
        Long categoriaId = 999L;
        Long marcaId = 1L;

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.cadastrarProduto(
                        produto, categoriaId, marcaId, List.of()
                )
        );

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoMarcaNaoExiste() {
        ProdutoEntity produto = new ProdutoEntity();

        Long categoriaId = 1L;
        Long marcaId = 999L;

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.of(new CategoriaEntity()));

        when(marcaRepository.findById(marcaId))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.cadastrarProduto(
                        produto, categoriaId, marcaId, List.of()
                )
        );

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveCadastrarProdutoComSucesso() {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("  Notebook  ");

        Long categoriaId = 1L;
        Long marcaId = 2L;

        CategoriaEntity categoria = new CategoriaEntity();
        MarcaEntity marca = new MarcaEntity();

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.of(categoria));

        when(marcaRepository.findById(marcaId))
                .thenReturn(Optional.of(marca));

        when(produtoRepository.save(produto))
                .thenReturn(produto);

        ProdutoEntity resultado = produtoService.cadastrarProduto(
                produto, categoriaId, marcaId, List.of()
        );

        assertEquals("Notebook", resultado.getNome());
        assertEquals(categoria, resultado.getCategoria());
        assertEquals(marca, resultado.getMarca());
        assertTrue(resultado.getEtiquetas().isEmpty());

        verify(produtoRepository).save(produto);
    }

    @Test
    void deveCadastrarProdutoComEtiquetas() {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("Notebook");

        Long categoriaId = 1L;
        Long marcaId = 2L;
        Long etiquetaId = 3L;

        CategoriaEntity categoria = new CategoriaEntity();
        MarcaEntity marca = new MarcaEntity();
        EtiquetaEntity etiqueta = new EtiquetaEntity();

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.of(categoria));

        when(marcaRepository.findById(marcaId))
                .thenReturn(Optional.of(marca));

        when(etiquetaRepository.findAllById(List.of(etiquetaId)))
                .thenReturn(List.of(etiqueta));

        when(produtoRepository.save(produto))
                .thenReturn(produto);

        ProdutoEntity resultado = produtoService.cadastrarProduto(
                produto,
                categoriaId,
                marcaId,
                List.of(etiquetaId)
        );

        assertEquals(1, resultado.getEtiquetas().size());
        assertEquals(etiqueta, resultado.getEtiquetas().get(0));

        verify(produtoRepository).save(produto);
    }

    @Test
    void deveLancarExcecaoQuandoEtiquetaNaoExiste() {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("Notebook");

        Long categoriaId = 1L;
        Long marcaId = 2L;
        Long etiquetaId = 999L;

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.of(new CategoriaEntity()));

        when(marcaRepository.findById(marcaId))
                .thenReturn(Optional.of(new MarcaEntity()));

        when(etiquetaRepository.findAllById(List.of(etiquetaId)))
                .thenReturn(List.of());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.cadastrarProduto(
                        produto,
                        categoriaId,
                        marcaId,
                        List.of(etiquetaId)
                )
        );

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoEtiquetaIdForNulo() {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("Notebook");

        Long categoriaId = 1L;
        Long marcaId = 2L;

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.of(new CategoriaEntity()));

        when(marcaRepository.findById(marcaId))
                .thenReturn(Optional.of(new MarcaEntity()));

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.cadastrarProduto(
                        produto,
                        categoriaId,
                        marcaId,
                        java.util.Arrays.asList((Long) null)
                )
        );

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveRetornarVazioQuandoProdutoParaAtualizarNaoExiste() {
        Long produtoId = 999L;

        ProdutoEntity produtoAtualizado = new ProdutoEntity();
        produtoAtualizado.setNome("Notebook");

        when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.empty());

        Optional<ProdutoEntity> resultado =
                produtoService.atualizarProduto(
                        produtoId,
                        produtoAtualizado,
                        1L,
                        2L,
                        List.of()
                );

        assertTrue(resultado.isEmpty());

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveAtualizarProdutoComSucesso() {
        Long produtoId = 1L;
        Long categoriaId = 2L;
        Long marcaId = 3L;

        ProdutoEntity produtoExistente = new ProdutoEntity();
        produtoExistente.setNome("Produto Antigo");

        ProdutoEntity produtoAtualizado = new ProdutoEntity();
        produtoAtualizado.setNome("  Notebook Novo  ");
        produtoAtualizado.setPreco(new BigDecimal("3500.00"));

        CategoriaEntity categoria = new CategoriaEntity();
        MarcaEntity marca = new MarcaEntity();

        when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.of(produtoExistente));

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.of(categoria));

        when(marcaRepository.findById(marcaId))
                .thenReturn(Optional.of(marca));

        when(produtoRepository.save(produtoExistente))
                .thenReturn(produtoExistente);

        Optional<ProdutoEntity> resultado =
                produtoService.atualizarProduto(
                        produtoId,
                        produtoAtualizado,
                        categoriaId,
                        marcaId,
                        List.of()
                );

        assertTrue(resultado.isPresent());
        assertEquals("Notebook Novo", resultado.get().getNome());
        assertEquals(new BigDecimal("3500.00"), resultado.get().getPreco());
        assertEquals(categoria, resultado.get().getCategoria());
        assertEquals(marca, resultado.get().getMarca());

        verify(produtoRepository).save(produtoExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoComCategoriaInexistente() {
        Long produtoId = 1L;
        Long categoriaId = 999L;
        Long marcaId = 2L;

        ProdutoEntity produtoExistente = new ProdutoEntity();

        ProdutoEntity produtoAtualizado = new ProdutoEntity();
        produtoAtualizado.setNome("Notebook");

        when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.of(produtoExistente));

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.atualizarProduto(
                        produtoId,
                        produtoAtualizado,
                        categoriaId,
                        marcaId,
                        List.of()
                )
        );

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoComMarcaInexistente() {
        Long produtoId = 1L;
        Long categoriaId = 2L;
        Long marcaId = 999L;

        ProdutoEntity produtoExistente = new ProdutoEntity();

        ProdutoEntity produtoAtualizado = new ProdutoEntity();
        produtoAtualizado.setNome("Notebook");

        when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.of(produtoExistente));

        when(categoriaRepository.findById(categoriaId))
                .thenReturn(Optional.of(new CategoriaEntity()));

        when(marcaRepository.findById(marcaId))
                .thenReturn(Optional.empty());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.atualizarProduto(
                        produtoId,
                        produtoAtualizado,
                        categoriaId,
                        marcaId,
                        List.of()
                )
        );

        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoComEtiquetaInexistente() {
        Long produtoId = 1L;
        Long categoriaId = 2L;
        Long marcaId = 3L;
        Long etiquetaId = 999L;

        ProdutoEntity produtoAtualizado = new ProdutoEntity();
        produtoAtualizado.setNome("Notebook");

        when(etiquetaRepository.findAllById(List.of(etiquetaId)))
                .thenReturn(List.of());

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.atualizarProduto(
                        produtoId,
                        produtoAtualizado,
                        categoriaId,
                        marcaId,
                        List.of(etiquetaId)
                )
        );

        verify(produtoRepository, never()).save(any());
    }
}