package br.edu.unoesc.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.DTO.ExemplarDTO;
import br.edu.unoesc.DTO.FilmeDTO;
import br.edu.unoesc.excecoes.ExcecaoPersonalizada;
import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.service.ExemplarService;
import br.edu.unoesc.service.FilmeService;

@Controller
@RequestMapping("/exemplar")
public class ExemplarController {

    @Autowired
    private ExemplarService exemplarService;

    @Autowired
    private FilmeService filmeService;

    @GetMapping("/consultar")
    public String listarExemplares(Model model) {
        List<ExemplarDTO> exemplares = exemplarService.listarTodos();
        List<FilmeDTO> filmes = filmeService.listarTodos();
        model.addAttribute("exemplares", exemplares);
        model.addAttribute("filmes", filmes);
        
        return "paginas/consulta/consultarExemplar";
    }
    
    @GetMapping("/cadastrar")
    public String cadastrarExemplar(ExemplarDTO exemplarDTO, Model model) {
        List<FilmeDTO> filmes = filmeService.listarFilmesAtivos();
        model.addAttribute("filmes", filmes);
        model.addAttribute("exemplarDTO", exemplarDTO);

        return "paginas/cadastro/cadastrarExemplar";
    }

    @PostMapping("/salvar")
    public String salvarExemplar(@ModelAttribute("exemplar") ExemplarDTO exemplarDTO, Model model) {
        try {
            exemplarService.adicionarExemplar(exemplarDTO);
            model.addAttribute("success", "Exemplar cadastrado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao cadastrar exemplar!");
            return "paginas/cadastro/cadastrarExemplar";
        }

        return "redirect:/exemplar/consultar";
    }
    
    @PostMapping("/editar")
    public String salvarExemplar(@ModelAttribute ExemplarDTO exemplarDTO, RedirectAttributes attr) {
        try {
            exemplarService.inativarExemplar(exemplarDTO);
            attr.addFlashAttribute("success", "Exemplar inativado com sucesso!");
            return "redirect:/exemplar/consultar";
        } catch (ExcecaoPersonalizada e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/exemplar/editar/" + exemplarDTO.id();
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao inativar exemplar!");
            return "redirect:/exemplar/editar/" + exemplarDTO.id();
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarExemplar(@PathVariable Integer id, Model model) {
        Exemplar exemplar = exemplarService.buscarPorId(id);

        if (exemplar != null) {
            ExemplarDTO exemplarDTO = new ExemplarDTO(exemplar);
            model.addAttribute("exemplarDTO", exemplarDTO);  
        }

        List<FilmeDTO> filmes = filmeService.listarFilmesAtivos();
        model.addAttribute("filmes", filmes);

        return "paginas/cadastro/cadastrarExemplar";
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> excluirExemplar(@PathVariable Integer id) {
        try {
        	exemplarService.deletarExemplar(id);
            return ResponseEntity.ok("Exemplar excluído com sucesso!");
        } catch (ExcecaoPersonalizada e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o filme");
	    }    	
    }
}
