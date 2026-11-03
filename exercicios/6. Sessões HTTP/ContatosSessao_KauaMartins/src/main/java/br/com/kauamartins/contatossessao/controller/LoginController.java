package br.com.kauamartins.contatossessao.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.kauamartins.contatossessao.model.Usuario;
import br.com.kauamartins.contatossessao.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/contatos";
    }

    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String email,
                             @RequestParam String senha,
                             HttpServletRequest request,
                             Model model) {
        Usuario usuario = usuarioService.autenticar(email == null ? null : email.trim(), senha);

        if (usuario == null) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            return "login";
        }

        HttpSession sessaoAnterior = request.getSession(false);
        if (sessaoAnterior != null) {
            sessaoAnterior.invalidate();
        }

        HttpSession sessao = request.getSession(true);
        sessao.setAttribute("usuarioLogado", usuario);
        sessao.setAttribute("favoritos", new ArrayList<Long>());

        return "redirect:/contatos";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            sessao.invalidate();
        }
        return "redirect:/login";
    }
}
