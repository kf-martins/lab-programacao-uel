package br.com.kauamartins.contatosmemoria.controller;

import br.com.kauamartins.contatosmemoria.model.Contact;
import br.com.kauamartins.contatosmemoria.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contatos")
public class ContactController {
    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contact> listar() {
        return service.listar();
    }

    @GetMapping("/{index}")
    public Contact buscar(@PathVariable int index) {
        return service.buscar(index);
    }

    @PostMapping
    public ResponseEntity<Contact> adicionar(@RequestBody Contact contato) {
        service.adicionar(contato);
        int indexCriado = service.listar().size() - 1;
        URI location = URI.create("/contatos/" + indexCriado);
        return ResponseEntity.created(location).body(contato);
    }

    @PutMapping("/{index}")
    public Contact atualizar(@PathVariable int index,
                             @RequestBody Contact contato) {
        service.atualizar(index, contato);
        return service.buscar(index);
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Map<String, String>> remover(@PathVariable int index) {
        service.remover(index);
        return ResponseEntity.ok(Map.of("mensagem", "Contato removido com sucesso."));
    }
}
