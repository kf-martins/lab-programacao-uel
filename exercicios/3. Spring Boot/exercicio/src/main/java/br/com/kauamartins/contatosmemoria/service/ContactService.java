package br.com.kauamartins.contatosmemoria.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.kauamartins.contatosmemoria.model.Contact;

@Service
public class ContactService {
    private final List<Contact> contatos = new ArrayList<>();

    public List<Contact> listar() {
        return new ArrayList<>(contatos);
    }

    public Contact buscar(int index) {
        validarIndice(index);
        return contatos.get(index);
    }

    public void adicionar(Contact contato) {
        if (contato == null) {
            throw new IllegalArgumentException("O contato enviado não pode ser nulo.");
        }
        contatos.add(contato);
    }

    public void remover(int index) {
        validarIndice(index);
        contatos.remove(index);
    }

    public void atualizar(int index, Contact contato) {
        validarIndice(index);
        if (contato == null) {
            throw new IllegalArgumentException("O contato enviado não pode ser nulo.");
        }
        contatos.set(index, contato);
    }

    private void validarIndice(int index) {
        if (index < 0 || index >= contatos.size()) {
            throw new IndexOutOfBoundsException(
                "Não existe contato no índice " + index + "."
            );
        }
    }
}
