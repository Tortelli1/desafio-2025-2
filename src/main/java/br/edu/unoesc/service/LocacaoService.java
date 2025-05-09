package br.edu.unoesc.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.DTO.LocacaoDTO;
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

    public Locacao adicionarLocacao(LocacaoDTO locacaoDTO, List<Integer> exemplarIds) {
        List<Exemplar> exemplares = exemplarRepository.findAllById(exemplarIds);
        Locacao locacao = locacaoDTO.constroiLocacao(exemplares);

        if (locacao.getExemplares().size() > 3) {
            throw new RuntimeException("Máximo de 3 exemplares por locação.");
        }

        if (locacao.getExemplares().stream().anyMatch(exemplar -> !exemplar.getAtivo())) {
            throw new RuntimeException("Todos os exemplares devem estar ativos.");
        }

        locacao.setDataLocacao(new Date());
        locacao.setQrCode(qrCodeService.gerarQrCode(locacao));
        return locacaoRepository.save(locacao);
    }


    public List<Locacao> listarLocacoesPendentes() {
        return locacaoRepository.findLocacoesPendentes();
    }

    public void devolverLocacao(Integer id) {
        Locacao locacao = locacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Locação não encontrada."));

        if (locacao.getDataDevolvido() != null) {
            return;
        }

        locacao.setDataDevolvido(new Date());
        locacaoRepository.save(locacao);

        locacao.getExemplares().forEach(e -> filmeService.atualizarExemplares(e.getFilme(), 1));
    }
}