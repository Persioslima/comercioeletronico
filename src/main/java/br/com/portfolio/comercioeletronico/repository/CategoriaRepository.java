package br.com.portfolio.comercioeletronico.repository;

import br.com.portfolio.comercioeletronico.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long > {

    @Query("""
            SELECT CASE WHEN COUNT(categoria) > 0 THEN true ELSE false END
            FROM CategoriaEntity categoria
            WHERE LOWER(TRIM(categoria.nome)) = LOWER(TRIM(:nome))
            """)

    boolean existeCategoriaComNome(@Param("nome") String nome);

    @Query("""
            SELECT CASE WHEN COUNT(categoria) > 0 THEN true ELSE false END
            FROM CategoriaEntity categoria
            WHERE LOWER(TRIM(categoria.nome)) = LOWER(TRIM(:nome))
            AND categoria.id <> :id
            """)

    boolean existeOutraCategoriaComNome(
            @Param("nome") String nome,
            @Param("id") Long id
    );
}
