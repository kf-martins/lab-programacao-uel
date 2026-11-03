package br.com.kauamartins.contatosmemoria.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<Map<String, String>> tratarIndiceInexistente(
            IndexOutOfBoundsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("erro", exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarRequisicaoInvalida(
            IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
            .body(Map.of("erro", exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarJsonInvalido(
            HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
            .body(Map.of("erro", "JSON inválido. Confira os campos e use dataNascimento no formato yyyy-MM-dd."));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> tratarParametroInvalido(
            MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.badRequest()
            .body(Map.of("erro", "O índice da URL deve ser um número inteiro."));
    }
}
