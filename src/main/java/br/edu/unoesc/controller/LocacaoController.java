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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.DTO.LocacaoDTO;
import br.edu.unoesc.excecoes.ExcecaoPersonalizada;
import br.edu.unoesc.model.Locacao;
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
    public String consultarLocacoes(
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "email", required = false) String email,
            Model model) {

        List<LocacaoDTO> pendentes;
        List<LocacaoDTO> devolvidos;

        boolean filtroAtivo = (cpf != null && !cpf.isBlank()) ||
                              (nome != null && !nome.isBlank()) ||
                              (email != null && !email.isBlank());

        if (filtroAtivo) {
            pendentes = locacaoService.buscarPendentesFiltrados(cpf, nome, email);
            devolvidos = locacaoService.buscarDevolvidosFiltrados(cpf, nome, email);
        } else {
            pendentes = locacaoService.listarPendentes();
            devolvidos = locacaoService.listarDevolvidos();
        }

        model.addAttribute("pendentes", pendentes);
        model.addAttribute("devolvidos", devolvidos);

        return "paginas/consulta/consultarLocacao";
    }

    @GetMapping("/cadastrar")
    public String cadastrarLocacoes(LocacaoDTO locacaoDTO, Model model) {
    	model.addAttribute("locacaoDTO", locacaoDTO);
        model.addAttribute("exemplares", exemplarService.listarExemplaresAtivos());
        return "paginas/cadastro/cadastrarLocacao";
    }

    @PostMapping("/salvar")
    public String salvarLocacao(
    		@ModelAttribute LocacaoDTO locacaoDTO, 
    		@RequestParam List<Integer> exemplarIds, 
    		RedirectAttributes attr) {
        try {
            locacaoService.adicionarLocacao(locacaoDTO, exemplarIds);
            attr.addFlashAttribute("success", "Locação realizada com sucesso!");
            return "redirect:/locacoes/consultar";
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao realizar locação: " + e.getMessage());
            return "redirect:/locacoes/cadastrar";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarLocacao(@PathVariable Integer id, Model model) {
        LocacaoDTO locacaoDTO = locacaoService.buscarDtoPorId(id);
        if (locacaoDTO == null) {
            model.addAttribute("erro", "Locação não encontrada.");
            return "redirect:/locacoes/consultar";
        }

        model.addAttribute("locacaoDTO", locacaoDTO);
        model.addAttribute("exemplares", exemplarService.listarExemplaresAtivos());

        return "paginas/cadastro/cadastrarLocacao";
    }
    
    @PostMapping("/atualizar")
    public String atualizarLocacao(
    		@ModelAttribute LocacaoDTO locacaoDTO,
    		@RequestParam("exemplarIds") List<Integer> exemplarIds,
            RedirectAttributes attr) {
        try {
            locacaoService.atualizarLocacao(locacaoDTO, exemplarIds);
            attr.addFlashAttribute("success", "Locação atualizada com sucesso!");
            return "redirect:/locacoes/consultar";
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao atualizar locação.");
            return "redirect:/locacoes/editar/" + locacaoDTO.id();
        }
    }
    
    @PostMapping("/devolver")
    public String confirmarDevolucao(
    		@RequestParam List<Integer> exemplarIds,
            @RequestParam Integer locacaoId,
            RedirectAttributes attr) {
        try {
            locacaoService.processarDevolucao(locacaoId, exemplarIds);
            attr.addFlashAttribute("success", "Devolução realizada com sucesso!");
            return "redirect:/locacoes/consultar";
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao realizar devolução!");
            return "redirect:/locacoes/devolver/" + locacaoId;
        }
    }
    
    @GetMapping("/devolver/{id}")
    public String devolverLocacao(
    		@PathVariable Integer id, 
    		Model model) {
        try {
            Locacao locacao = locacaoService.buscarPorId(id);
            model.addAttribute("locacoes", List.of(locacao));
            model.addAttribute("exemplares", locacao.getExemplares());
            
            return "/paginas/devolverLocacao";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Erro ao devolver a locação!");
            return "redirect:/locacoes/consultar";
        }
    }
    
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarLocacao(@PathVariable Integer id) {
        try {
            locacaoService.excluirLocacao(id);
            return ResponseEntity.ok("Locação excluída com sucesso!");
        } catch (ExcecaoPersonalizada e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível excluir a locação!");
        }
    }
    
}
