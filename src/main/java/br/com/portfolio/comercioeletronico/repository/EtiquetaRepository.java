package br.com.portfolio.comercioeletronico.repository;

import br.com.portfolio.comercioeletronico.entity.EtiquetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EtiquetaRepository extends JpaRepository<EtiquetaEntity, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM EtiquetaEntity e
            WHERE LOWER(TRIM(e.nome)) = LOWER(TRIM(:nome))
            """)
    boolean existeEtiquetaComNome(@Param("nome") String nome);

    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM EtiquetaEntity e
            WHERE LOWER(TRIM(e.nome)) = LOWER(TRIM(:nome))
            AND e.id <> :id
            """)
    boolean existeOutraEtiquetaComNome(
            @Param("nome") String nome,
            @Param("id") Long id
    );
}