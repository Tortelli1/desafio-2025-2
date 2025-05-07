package br.edu.unoesc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.unoesc.DTO.ExemplarDTO;
import br.edu.unoesc.DTO.FilmeDTO;
import br.edu.unoesc.service.ExemplarService;
import br.edu.unoesc.service.FilmeService;

@Controller
@RequestMapping("/exemplar")
public class ExemplarController {

    @Autowired
    private ExemplarService exemplarService;

    @Autowired
    private FilmeService filmeService;
    
    @GetMapping("/cadastrar")
    public String cadastrarExemplar(Model model) {
        List<FilmeDTO> filmes = filmeService.listarFilmesAtivos();
        model.addAttribute("filmes", filmes);
        model.addAttribute("exemplar", new ExemplarDTO(null, null, null, null));

        return "paginas/cadastro/cadastrarExemplar";
    }
    

    @PostMapping("/salvar")
    public String salvarExemplar(@ModelAttribute("exemplar") ExemplarDTO exemplarDTO, Model model) {
        try {
            exemplarService.adicionarExemplar(exemplarDTO);
            model.addAttribute("success", "Exemplar cadastrado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao cadastrar exemplar: " + e.getMessage());
            return "paginas/cadastro/cadastrarExemplar";
        }

        return "redirect:/exemplar/cadastrar";
    }
    

    
    @GetMapping("/listar")
    public String listarExemplares(Model model) {
        model.addAttribute("exemplares", exemplarService.listarTodos());
        return "paginas/consulta/exemplar/listarExemplar";
    }

    @DeleteMapping("/excluir/{id}")
    public String inativarExemplar(@PathVariable Integer id, Model model) {
        try {
            exemplarService.deletarExemplar(id);
            model.addAttribute("success", "Exemplar excluído com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/exemplar/listarExemplar";
    }
    
}
