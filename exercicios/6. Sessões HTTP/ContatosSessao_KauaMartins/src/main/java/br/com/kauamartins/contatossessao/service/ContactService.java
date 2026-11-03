package br.com.kauamartins.contatossessao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.kauamartins.contatossessao.model.Contact;
import br.com.kauamartins.contatossessao.repository.ContactRepository;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional(readOnly = true)
    public List<Contact> listar() {
        return contactRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional(readOnly = true)
    public Contact buscar(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    @Transactional
    public Contact salvar(Contact contato) {
        return contactRepository.save(contato);
    }

    @Transactional
    public Contact atualizar(Long id, Contact dados) {
        Contact contato = buscar(id);
        contato.setNome(dados.getNome());
        contato.setTelefone(dados.getTelefone());
        contato.setEmail(dados.getEmail());
        contato.setEndereco(dados.getEndereco());
        contato.setDataNascimento(dados.getDataNascimento());
        return contato;
    }

    @Transactional
    public void remover(Long id) {
        contactRepository.delete(buscar(id));
    }

    public List<Contact> listarFavoritos(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, Contact> contatosPorId = new HashMap<>();
        for (Contact contato : contactRepository.findAllById(ids)) {
            contatosPorId.put(contato.getId(), contato);
        }

        List<Contact> resultado = new ArrayList<>();
        for (Long id : ids) {
            Contact contato = contatosPorId.get(id);
            if (contato != null) {
                resultado.add(contato);
            }
        }
        return resultado;
    }

    public boolean existe(Long id) {
        return id != null && contactRepository.existsById(id);
    }

    public static class ContactNotFoundException extends RuntimeException {
        public ContactNotFoundException(Long id) {
            super("Não existe contato com o id " + id + ".");
        }
    }
}
