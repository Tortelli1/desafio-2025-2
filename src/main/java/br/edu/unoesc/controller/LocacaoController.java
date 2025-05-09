package br.edu.unoesc.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.DTO.LocacaoDTO;
import br.edu.unoesc.service.ExemplarService;
import br.edu.unoesc.service.LocacaoService;

@Controller
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @Autowired
    private ExemplarService exemplarService;

    @GetMapping("/consultar")
    public String consultarLocacoes(Model model) {
        model.addAttribute("locacoes", locacaoService.listarLocacoesPendentes());
        return "paginas/consulta/consultarLocacao";
    }

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("exemplares", exemplarService.listarExemplaresAtivos());
        return "paginas/cadastro/cadastrarLocacao";
    }

    @PostMapping("/salvar")
    public String salvarLocacao(@ModelAttribute LocacaoDTO locacaoDTO, @RequestParam List<Integer> exemplarIds, RedirectAttributes attr) {
        try {
            locacaoService.adicionarLocacao(locacaoDTO, exemplarIds);
            attr.addFlashAttribute("success", "Locação realizada com sucesso!");
            return "redirect:/locacoes/consultar";
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao realizar locação: " + e.getMessage());
            return "redirect:/locacoes/cadastrar";
        }
    }
}
