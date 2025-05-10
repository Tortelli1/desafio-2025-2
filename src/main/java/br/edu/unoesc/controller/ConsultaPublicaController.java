package br.edu.unoesc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unoesc.model.Locacao;
import br.edu.unoesc.service.LocacaoService;

@Controller
@RequestMapping("/consultaPublica")
public class ConsultaPublicaController {

	@Autowired
    private LocacaoService locacaoService;

    @GetMapping("/consultarLocacao")
    public String exibirFormularioConsulta() {
        return "paginas/consulta/consultaPublica";
    }

    @GetMapping("/resultado")
    public String exibirResultadoConsulta(@RequestParam String cpf, Model model) {
        List<Locacao> locacoes = locacaoService.buscarLocacoesPendentesPorCpf(cpf);
        model.addAttribute("cpf", cpf);
        model.addAttribute("locacoes", locacoes);
        return "paginas/consulta/consultaPublica";
    }
}