package br.edu.unoesc.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.DTO.FilmeDTO;
import br.edu.unoesc.model.Filme;
import br.edu.unoesc.service.FilmeService;
import br.edu.unoesc.service.TmdbService;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

	@Autowired
	private FilmeService filmeService;
	
	@Autowired
	private TmdbService tmdbService;
	
	@GetMapping("/consultar")
	public String listarFilmes(FilmeDTO filmeDTO, Model model) {
		model.addAttribute("filmeDTO", filmeService.listarFilmesAtivos());
		return "paginas/consulta/consultarFilme";
		
	}
	
	@GetMapping("/cadastrar")
	public String cadastrarFilme(FilmeDTO filmeDTO, Model model) {
		model.addAttribute("filmeDTO", filmeDTO);
		return "paginas/cadastro/cadastrarFilme";
	}
	
	@PostMapping("/salvar")
	public String salvarFilme(FilmeDTO filmeDTO, Model model, RedirectAttributes attr) {
		try {
			filmeService.adicionarFilme(new Filme(filmeDTO));
			attr.addFlashAttribute("success", "Filme cadastrado com sucesso!");
			return "redirect:/filmes/consultar";
		} catch (Exception e) {
			attr.addFlashAttribute("error", "Erro ao cadastrar filme!");
			return "redirect:/filmes/cadastrar";
		}
	}
	
	@PostMapping("/editar")
	public String editarFilme(FilmeDTO filmeDTO, RedirectAttributes attr) {
	    try {
	        filmeService.atualizarFilme(filmeDTO);
	        attr.addFlashAttribute("success", "Filme atualizado com sucesso!");
	        return "redirect:/filmes/consultar";
	    } catch (Exception e) {
	        attr.addFlashAttribute("error", "Erro ao atualizar filme!");
	        return "redirect:/filmes/editar/" + filmeDTO.id();
	    }
	}
	
	@GetMapping("/editar/{id}")
	public String editarFilme(@PathVariable Integer id, Model model) {
	    FilmeDTO filme = filmeService.buscarDtoPorId(id);
	    model.addAttribute("filmeDTO", filme);
	    return "paginas/cadastro/cadastrarFilme";
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<String> deletarFilme(@PathVariable Integer id) {
	    try {
	        filmeService.deletarFilme(id);
	        return ResponseEntity.ok("Filme excluído com sucesso");
	    } catch (Exception e) {
	    	// stack trace somente para debug, remover
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o filme");
	    }
	}

	@GetMapping("/buscar-filmes")
	@ResponseBody
	public ResponseEntity<?> buscarFilmes(@RequestParam String titulo) {
	    try {
	        List<FilmeDTO> filmes = tmdbService.buscarFilmesPorTitulo(titulo);
	        return ResponseEntity.ok(filmes);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Erro ao buscar filmes");
	    }
	}

	@GetMapping("/filme-tmdb")
	@ResponseBody
	public ResponseEntity<FilmeDTO> obterDetalhesFilme(@RequestParam Integer id) {
	    try {
	        FilmeDTO filme = tmdbService.buscarFilmePorId(id);
	        return ResponseEntity.ok(filme);
	    } catch (Exception e) {
	    	// nao devolver as mensagens de erro diretamente, dizer somente que ocooreu um erro ao deletar
	        return ResponseEntity.badRequest().body(null);
	    }
	}
	
}
