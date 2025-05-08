package br.edu.unoesc.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import br.edu.unoesc.DTO.LocacaoDTO;
import br.edu.unoesc.model.Locacao;
import br.edu.unoesc.service.ExemplarService;
import br.edu.unoesc.service.FilmeService;
import br.edu.unoesc.service.LocacaoService;
import br.edu.unoesc.service.QRCodeService;

@Controller
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @Autowired
    private ExemplarService exemplarService;
    
    @Autowired
    private QRCodeService qrCodeService;
	
	@GetMapping("/consultar")
	public String consultarLocacoes(Model model) {
		model.addAttribute("locacoes", locacaoService.listarLocacoesPendentes());
        return "paginas/consulta/consultarLocacao";
	}
	
    @GetMapping("/cadastrar")
    public String cadastrarLocacao(LocacaoDTO locacaoDTO, Model model) {
        model.addAttribute("locacaoDTO", locacaoDTO);
        model.addAttribute("exemplares", exemplarService.listarExemplaresAtivos()); 
        return "paginas/cadastro/cadastrarLocacao";
    }

    @PostMapping("/salvar")
    public String salvarLocacao(LocacaoDTO locacaoDTO, Model model, RedirectAttributes attr) {
        try {
            Locacao locacao = new Locacao(locacaoDTO);
            List<Integer> exemplaresIds = locacaoDTO.listaExemplares().stream()
                .map(e -> e.id())
                .collect(Collectors.toList());

            locacaoService.alugar(locacao, exemplaresIds);

            attr.addFlashAttribute("success", "Locação realizada com sucesso!");
            return "redirect:/locacoes/consultar";
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao realizar locação!");
            return "redirect:/locacoes/cadastrar";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarLocacao(@PathVariable Integer id, Model model) {
        try {
            Locacao locacao = locacaoService.buscarLocacaoPorId(id);
            model.addAttribute("locacao", locacao);
            model.addAttribute("exemplares", exemplarService.listarExemplaresAtivos());
            return "paginas/cadastro/editarLocacao";
        } catch (Exception e) {
            return "redirect:/locacoes/consultar";
        }
    }

    @PostMapping("/devolver")
    public String devolverLocacao(@RequestParam Integer id, RedirectAttributes attr) {
        try {
            locacaoService.devolver(id);
            attr.addFlashAttribute("success", "Locação devolvida com sucesso!");
            return "redirect:/locacoes/consultar";
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Erro ao devolver locação!");
            return "redirect:/locacoes/consultar";
        }
    }

    @GetMapping("/gerar-qrcode/{id}")
    public String gerarQRCode(@PathVariable Integer id, Model model) {
        try {
            Locacao locacao = locacaoService.buscarLocacaoPorId(id);
            String qrCodeBase64 = qrCodeService.gerarQrCode(locacao);
            model.addAttribute("qrCodeBase64", qrCodeBase64);
            return "paginas/consulta/qrCodeLocacao";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao gerar QR Code!");
            return "redirect:/locacoes/consultar";
        }
    }

    @GetMapping("/buscar-locacoes")
    @ResponseBody
    public ResponseEntity<?> buscarLocacoes(@RequestParam String cpf) {
        try {
            List<LocacaoDTO> locacoes = locacaoService.buscarLocacoesPorCpf(cpf);
            return ResponseEntity.ok(locacoes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar locações");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarLocacao(@PathVariable Integer id) {
        try {
            locacaoService.deletarLocacao(id);
            return ResponseEntity.ok("Locação excluída com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir locação");
        }
    }
    
}
