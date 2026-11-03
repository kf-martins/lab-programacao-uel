package br.com.kauamartins.contatosbd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.kauamartins.contatosbd.model.Contact;
import br.com.kauamartins.contatosbd.repository.ContactRepository;

@Service
public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Contact> listarContatos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Contact buscarContato(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ContactNotFoundException(id));
    }

    @Transactional
    public Contact adicionarContato(Contact contato) {
        return repository.save(contato);
    }

    @Transactional
    public void removerContato(Long id) {
        Contact contato = buscarContato(id);
        repository.delete(contato);
    }

    @Transactional
    public Contact atualizarContato(Long id, Contact contato) {
        Contact existente = buscarContato(id);
        existente.setNome(contato.getNome());
        existente.setTelefone(contato.getTelefone());
        existente.setEmail(contato.getEmail());
        existente.setEndereco(contato.getEndereco());
        existente.setDataNascimento(contato.getDataNascimento());
        return repository.save(existente);
    }

    public static class ContactNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        private final Long id;

        public ContactNotFoundException(Long id) {
            super("Não existe contato com o id " + id + ".");
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }
}
