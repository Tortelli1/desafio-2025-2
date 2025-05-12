package br.edu.unoesc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unoesc.model.Locacao;
import br.edu.unoesc.service.LocacaoService;
import br.edu.unoesc.service.QRCodeService;

@Controller
@RequestMapping("/consultaPublica")
public class ConsultaPublicaController {

	@Autowired
    private LocacaoService locacaoService;

	@Autowired
	private QRCodeService qrCodeService;

    @GetMapping("/consultarLocacao")
    public String exibirFormularioConsulta() {
        return "paginas/consulta/consultaPublica";
    }

    @GetMapping("/resultado")
    public String exibirResultadoConsulta(@RequestParam String cpf, Model model) {
        List<Locacao> locacoes = locacaoService.buscarLocacoesPendentesPorCpf(cpf);

        Map<Integer, String> qrCodes = new HashMap<>();
        for (Locacao locacao : locacoes) {
            qrCodes.put(locacao.getId(), qrCodeService.gerarQrCode(locacao));
        }

        model.addAttribute("cpf", cpf);
        model.addAttribute("locacoes", locacoes);
        model.addAttribute("qrCodes", qrCodes);

        return "paginas/consulta/consultaPublica";
    }
}