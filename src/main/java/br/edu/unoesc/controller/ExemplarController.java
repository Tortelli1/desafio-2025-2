package br.edu.unoesc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
        exemplares.forEach(exemplar -> {
            Optional<FilmeDTO> filme = filmes.stream()
                .filter(f -> f.id().equals(exemplar.filmeId()))
                .findFirst();

            filme.ifPresent(f -> {
                ExemplarDTO exemplarAtualizado = new ExemplarDTO(
                    exemplar.id(),
                    exemplar.dataCadastro(),
                    exemplar.ativo(),
                    exemplar.filmeId(),
                    f.titulo()
                );
            });
        });
        model.addAttribute("exemplares", exemplares);
        model.addAttribute("filmes", filmes);
        
        return "paginas/consulta/consultarExemplar";
    }
    
    @GetMapping("/cadastrar")
    public String cadastrarExemplar(Model model) {
        List<FilmeDTO> filmes = filmeService.listarFilmesAtivos();
        model.addAttribute("filmes", filmes);
        model.addAttribute("exemplar", new ExemplarDTO(null, null, null, null, null));

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

        return "redirect:/exemplar/consultar";
    }
    
    @PostMapping("/editar")
    public String salvarEdicaoExemplar(@ModelAttribute ExemplarDTO exemplarDTO, RedirectAttributes attr) {
        try {
            exemplarService.inativarExemplar(exemplarDTO);
            attr.addFlashAttribute("success", "Exemplar inativado com sucesso!");
            return "redirect:/exemplar/consultar";
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao inativar exemplar: " + e.getMessage());
            return "redirect:/exemplar/editar/" + exemplarDTO.id();
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarExemplar(@PathVariable Integer id, Model model) {
        Exemplar exemplar = exemplarService.buscarPorId(id);

        if (exemplar != null) {
            ExemplarDTO exemplarDTO = new ExemplarDTO(exemplar);
            model.addAttribute("exemplar", exemplarDTO);  
        }

        List<FilmeDTO> filmes = filmeService.listarFilmesAtivos();
        model.addAttribute("filmes", filmes);

        return "paginas/cadastro/cadastrarExemplar";
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> excluirExemplar(@PathVariable Integer id) {
        exemplarService.deletarExemplar(id);
        return ResponseEntity.noContent().build();
    }
}
