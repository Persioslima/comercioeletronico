
package br.com.portfolio.comercioeletronico.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorGlobalException {

    @ExceptionHandler(ConflitoNegocioException.class)
    public ResponseEntity<Map<String, String>> tratarConflito(
            ConflitoNegocioException excecao) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("erro", excecao.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarViolacaoIntegridade(
            DataIntegrityViolationException excecao) {

        if (restricaoDuplicada(excecao, "idx_categorias_nome_normalizado")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Categoria existente!"));
        }

        if (restricaoDuplicada(excecao, "idx_marcas_nome_normalizado")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Marca existente!"));
        }

        throw excecao;
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException excecao) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", excecao.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, String>>> tratarErroValidacao(
            MethodArgumentNotValidException excecao) {

        Map<String, String> erros = new HashMap<>();

        excecao.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.put(
                        erro.getField(),
                        erro.getDefaultMessage()
                ));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erros", erros));
    }


    private boolean restricaoDuplicada(Throwable causa, String nomeIndice) {

        while (causa != null) {

            if (causa instanceof SQLException erro
                    && "23505".equals(erro.getSQLState())
                    && erro.getMessage() != null
                    && erro.getMessage().contains(nomeIndice)) {
                return true;
            }

            causa = causa.getCause();
        }

        return false;
    }
}
