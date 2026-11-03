package br.com.kauamartins.contatosbd.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kauamartins.contatosbd.model.Contact;
import br.com.kauamartins.contatosbd.service.ContactService;

@RestController
@RequestMapping("/contatos")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> listarContatos() {
        return ResponseEntity.ok(service.listarContatos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> buscarContato(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarContato(id));
    }

    @PostMapping
    public ResponseEntity<Contact> adicionarContato(@Valid @RequestBody Contact contato) {
        Contact criado = service.adicionarContato(contato);
        return ResponseEntity.created(URI.create("/contatos/" + criado.getId()))
            .body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> atualizarContato(@PathVariable Long id,
                                                     @Valid @RequestBody Contact contato) {
        return ResponseEntity.ok(service.atualizarContato(id, contato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerContato(@PathVariable Long id) {
        service.removerContato(id);
        return ResponseEntity.noContent().build();
    }
}
