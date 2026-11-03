package br.com.kauamartins.contatosbd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kauamartins.contatosbd.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
