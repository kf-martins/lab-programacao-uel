package br.com.kauamartins.contatosmemoria.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kauamartins.contatosmemoria.model.Contact;
import br.com.kauamartins.contatosmemoria.service.ContactService;

@RestController
@RequestMapping("/contatos")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{index}")
    public ResponseEntity<?> buscar(@PathVariable int index) {
        try {
            return ResponseEntity.ok(service.buscar(index));
        } catch (IndexOutOfBoundsException exception) {
            return contatoNaoEncontrado(exception);
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody(required = false) Contact contato) {
        try {
            service.adicionar(contato);
            int indexCriado = service.listar().size() - 1;
            URI location = URI.create("/contatos/" + indexCriado);
            return ResponseEntity.created(location).body(contato);
        } catch (IllegalArgumentException exception) {
            return requisicaoInvalida(exception);
        }
    }

    @PutMapping("/{index}")
    public ResponseEntity<?> atualizar(@PathVariable int index,
                                       @RequestBody(required = false) Contact contato) {
        try {
            service.atualizar(index, contato);
            return ResponseEntity.ok(service.buscar(index));
        } catch (IndexOutOfBoundsException exception) {
            return contatoNaoEncontrado(exception);
        } catch (IllegalArgumentException exception) {
            return requisicaoInvalida(exception);
        }
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> remover(@PathVariable int index) {
        try {
            service.remover(index);
            return ResponseEntity.noContent().build();
        } catch (IndexOutOfBoundsException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<Map<String, String>> contatoNaoEncontrado(
            IndexOutOfBoundsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("erro", exception.getMessage()));
    }

    private ResponseEntity<Map<String, String>> requisicaoInvalida(
            IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
            .body(Map.of("erro", exception.getMessage()));
    }
}
