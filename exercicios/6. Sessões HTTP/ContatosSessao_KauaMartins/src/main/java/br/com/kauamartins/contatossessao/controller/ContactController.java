package br.com.kauamartins.contatossessao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.kauamartins.contatossessao.model.Contact;
import br.com.kauamartins.contatossessao.model.Usuario;
import br.com.kauamartins.contatossessao.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contatos")
    public String listarContatos(HttpServletRequest request, Model model) {
        HttpSession sessao = obterSessaoAutenticada(request);
        if (sessao == null) {
            return "redirect:/login";
        }

        List<Long> favoritos = obterFavoritos(sessao);
        model.addAttribute("contatos", contactService.listar());
        model.addAttribute("favoritos", favoritos);
        model.addAttribute("usuario", (Usuario) sessao.getAttribute("usuarioLogado"));
        return "contatos";
    }

    @GetMapping("/contatos/novo")
    public String novoContato(HttpServletRequest request, Model model) {
        if (obterSessaoAutenticada(request) == null) {
            return "redirect:/login";
        }
        model.addAttribute("contato", new Contact());
        model.addAttribute("tituloFormulario", "Novo contato");
        return "contato-form";
    }

    @PostMapping("/contatos")
    public String criarContato(@Valid Contact contato,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (obterSessaoAutenticada(request) == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloFormulario", "Novo contato");
            return "contato-form";
        }
        contactService.salvar(contato);
        redirectAttributes.addFlashAttribute("sucesso", "Contato criado com sucesso.");
        return "redirect:/contatos";
    }

    @GetMapping("/contatos/{id}/editar")
    public String editarContato(@PathVariable Long id,
                                HttpServletRequest request,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (obterSessaoAutenticada(request) == null) {
            return "redirect:/login";
        }
        try {
            model.addAttribute("contato", contactService.buscar(id));
        } catch (ContactService.ContactNotFoundException exception) {
            redirectAttributes.addFlashAttribute("erro", exception.getMessage());
            return "redirect:/contatos";
        }
        model.addAttribute("tituloFormulario", "Editar contato");
        return "contato-form";
    }

    @PostMapping("/contatos/{id}")
    public String atualizarContato(@PathVariable Long id,
                                   @Valid Contact contato,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (obterSessaoAutenticada(request) == null) {
            return "redirect:/login";
        }
        contato.setId(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloFormulario", "Editar contato");
            return "contato-form";
        }
        try {
            contactService.atualizar(id, contato);
        } catch (ContactService.ContactNotFoundException exception) {
            redirectAttributes.addFlashAttribute("erro", exception.getMessage());
            return "redirect:/contatos";
        }
        redirectAttributes.addFlashAttribute("sucesso", "Contato atualizado com sucesso.");
        return "redirect:/contatos";
    }

    @PostMapping("/contatos/{id}/excluir")
    public String excluirContato(@PathVariable Long id,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        HttpSession sessao = obterSessaoAutenticada(request);
        if (sessao == null) {
            return "redirect:/login";
        }
        try {
            contactService.remover(id);
            obterFavoritos(sessao).remove(id);
            redirectAttributes.addFlashAttribute("sucesso", "Contato removido com sucesso.");
        } catch (ContactService.ContactNotFoundException exception) {
            redirectAttributes.addFlashAttribute("erro", exception.getMessage());
        }
        return "redirect:/contatos";
    }

    @PostMapping("/contatos/{id}/favoritar")
    public String alternarFavorito(@PathVariable Long id,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {
        HttpSession sessao = obterSessaoAutenticada(request);
        if (sessao == null) {
            return "redirect:/login";
        }

        if (!contactService.existe(id)) {
            redirectAttributes.addFlashAttribute("erro", "Contato não encontrado.");
            return "redirect:/contatos";
        }

        List<Long> favoritos = obterFavoritos(sessao);
        if (favoritos.contains(id)) {
            favoritos.remove(id);
        } else {
            favoritos.add(id);
        }

        sessao.setAttribute("favoritos", favoritos);
        return "redirect:/contatos";
    }

    @GetMapping("/favoritos")
    public String listarFavoritos(HttpServletRequest request, Model model) {
        HttpSession sessao = obterSessaoAutenticada(request);
        if (sessao == null) {
            return "redirect:/login";
        }

        List<Long> favoritos = obterFavoritos(sessao);
        model.addAttribute("contatos", contactService.listarFavoritos(favoritos));
        model.addAttribute("usuario", (Usuario) sessao.getAttribute("usuarioLogado"));
        return "favoritos";
    }

    private HttpSession obterSessaoAutenticada(HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuarioLogado") == null) {
            return null;
        }
        return sessao;
    }

    @SuppressWarnings("unchecked")
    private List<Long> obterFavoritos(HttpSession sessao) {
        Object atributo = sessao.getAttribute("favoritos");
        if (atributo instanceof List<?>) {
            return (List<Long>) atributo;
        }

        List<Long> favoritos = new ArrayList<>();
        sessao.setAttribute("favoritos", favoritos);
        return favoritos;
    }
}
