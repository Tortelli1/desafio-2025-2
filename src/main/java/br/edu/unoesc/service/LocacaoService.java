package br.edu.unoesc.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.DTO.LocacaoDTO;
import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.model.Locacao;
import br.edu.unoesc.repository.LocacaoRepository;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ExemplarService exemplarService;

    public List<Locacao> listarLocacoesPendentes() {
        return locacaoRepository.findByDataDevolvidoIsNull();
    }

    public void adicionarLocacao(LocacaoDTO locacaoDTO, List<Integer> exemplarIds) {
        List<Exemplar> exemplares = exemplarService.buscarPorId(exemplarIds);
        Locacao locacao = new Locacao(locacaoDTO);
        locacao.setExemplares(exemplares);
        locacaoRepository.save(locacao);
    }

    public void atualizarLocacao(LocacaoDTO dto, List<Integer> exemplarIds) {
        Locacao locacao = locacaoRepository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException("Locação não encontrada"));
        locacao.setNome(dto.nome());
        locacao.setCpf(dto.cpf());
        locacao.setDataLocacao(dto.dataLocacao());
        locacao.setDataDevolvido(dto.dataDevolvido());

        List<Exemplar> exemplares = exemplarService.buscarPorId(exemplarIds);
        locacao.setExemplares(exemplares);

        locacaoRepository.save(locacao);
    }
    
    
    public LocacaoDTO buscarDtoPorId(Integer id) {
        Locacao locacao = locacaoRepository.findById(id).orElse(null);
        if (locacao != null) {
            return new LocacaoDTO(locacao);
        }
        return null;
    }

    public void excluirLocacao(Integer id) {
        locacaoRepository.deleteById(id);
    }
}