package br.edu.unoesc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.service.ExemplarService;
import br.edu.unoesc.service.FilmeService;

@Controller
public class ExemplarController {

	@Autowired
	private FilmeService filmeService;

	@Autowired
	private ExemplarService exemplarService;
	
   @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("filmes", filmeService.listarFilmesAtivos());
        model.addAttribute("exemplarRequestDTO", new ExemplarRequestDTO());
        return "exemplar/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarExemplar(@ModelAttribute ExemplarRequestDTO exemplarDTO, RedirectAttributes redirectAttributes) {
        try {
            exemplarService.cadastrarExemplar(exemplarDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Exemplar cadastrado com sucesso!");
            return "redirect:/exemplar/cadastro";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar exemplar: " + e.getMessage());
            return "redirect:/exemplar/cadastro";
        }
    }
}
