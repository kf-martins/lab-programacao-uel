package br.com.kauamartins.contatossessao.service;

import org.springframework.stereotype.Service;

import br.com.kauamartins.contatossessao.model.Usuario;
import br.com.kauamartins.contatossessao.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            return null;
        }

        return usuarioRepository.findByEmailAndSenha(email, senha)
                .orElse(null);
    }
}
