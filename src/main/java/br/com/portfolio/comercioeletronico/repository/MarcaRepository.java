package br.com.portfolio.comercioeletronico.repository;

import br.com.portfolio.comercioeletronico.entity.MarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(marca) > 0 THEN true ELSE false END
            FROM MarcaEntity marca
            WHERE LOWER(TRIM(marca.nome)) = LOWER(TRIM(:nome))
            """)
    boolean existeMarcaComNome(@Param("nome") String nome);

    @Query("""
            SELECT CASE WHEN COUNT(marca) > 0 THEN true ELSE false END
            FROM MarcaEntity marca
            WHERE LOWER(TRIM(marca.nome)) = LOWER(TRIM(:nome))
            AND marca.id <> :id
            """)
    boolean existeOutraMarcaComNome(
            @Param("nome") String nome,
            @Param("id") Long id
    );
}