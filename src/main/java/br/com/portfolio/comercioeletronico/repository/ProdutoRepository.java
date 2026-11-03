package br.com.portfolio.comercioeletronico.repository;

import br.com.portfolio.comercioeletronico.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    boolean existsByCategoria_Id(Long categoriaId);
    boolean existsByMarca_Id(Long marcaId);
    boolean existsByEtiquetas_Id(Long etiquetaId);

    Page<ProdutoEntity> findByNomeContainingIgnoreCase(
            String nome,
            Pageable pageable
    );

    Page<ProdutoEntity> findByCategoria_Id(
            Long categoriaId,
            Pageable pageable
    );

    Page<ProdutoEntity> findByMarca_Id(
            Long marcaId,
            Pageable pageable
    );

    @Query("""
        SELECT p FROM ProdutoEntity p
        WHERE (CAST(:nome AS string) IS NULL
               OR LOWER(p.nome) LIKE LOWER(CONCAT('%', CAST(:nome AS string), '%')))
        AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)
        AND (:marcaId IS NULL OR p.marca.id = :marcaId)
        """)
    Page<ProdutoEntity> buscarProdutosComFiltros(
            @Param("nome") String nome,
            @Param("categoriaId") Long categoriaId,
            @Param("marcaId") Long marcaId,
            Pageable pageable
    );
}
