package br.edu.unoesc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.model.Locacao;
import br.edu.unoesc.repository.ExemplarRepository;
import br.edu.unoesc.repository.LocacaoRepository;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ExemplarRepository exemplarRepository;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private FilmeService filmeService;

    public Locacao alugar(Locacao locacao, List<Integer> idsExemplares) {
        if (idsExemplares.size() > 3) throw new RuntimeException("Máximo 3 exemplares.");

        List<Exemplar> exemplares = exemplarRepository.findAllById(idsExemplares);
        if (exemplares.size() != idsExemplares.size() || exemplares.stream().anyMatch(e -> !e.getAtivo())) {
            throw new RuntimeException("Todos os exemplares devem estar ativos.");
        }

        locacao.setDataLocacao(new Date());
        locacao.setQrCode(qrCodeService.gerarQrCode(locacao)); // base64 string
        locacao.setExemplares(exemplares);
        Locacao salva = locacaoRepository.save(locacao);

        exemplares.forEach(e -> filmeService.atualizarExemplares(e.getFilme(), -1));

        return salva;
    }

    public void devolver(Integer id) {
        Locacao locacao = locacaoRepository.findById(id).orElseThrow();
        if (locacao.getDataDevolvido() != null) return;

        locacao.setDataDevolvido(new Date());
        locacaoRepository.save(locacao);

        locacao.getExemplares()
            .forEach(e -> filmeService.atualizarExemplares(e.getFilme(), 1));
    }
}
