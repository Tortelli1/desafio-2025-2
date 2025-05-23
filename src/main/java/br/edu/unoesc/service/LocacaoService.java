package br.edu.unoesc.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.DTO.LocacaoDTO;
import br.edu.unoesc.excecoes.ExcecaoPersonalizada;
import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.model.Filme;
import br.edu.unoesc.model.Locacao;
import br.edu.unoesc.repository.LocacaoRepository;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ExemplarService exemplarService;
    
    @Autowired
    private FilmeService filmeService;
    
    @Autowired
    private QRCodeService qrCodeService;

    public List<Locacao> listarTodos() {
    	return locacaoRepository.findAll();
    }
    
    public List<LocacaoDTO> listarDevolvidos() {
        return locacaoRepository.findByDataDevolvidoIsNotNull()
                .stream()
                .map(LocacaoDTO::new)
                .toList();
    }
    
    public List<LocacaoDTO> listarPendentes() {
        return locacaoRepository.findByDataDevolvidoIsNull()
                .stream()
                .map(LocacaoDTO::new)
                .toList();
    }

    public List<LocacaoDTO> buscarLocacoesPendentesPorCpf(String cpf) {
        return locacaoRepository.findByCpfContainingIgnoreCaseAndDataDevolvidoIsNull(cpf)
                .stream()
                .map(LocacaoDTO::new)
                .toList();
    }
    
    public List<LocacaoDTO> buscarPendentesFiltrados(String cpf, String nome, String email, String filme) {
        List<Locacao> locacoes;

        if (cpf != null && !cpf.isBlank()) {
            locacoes = locacaoRepository.findByCpfContainingIgnoreCaseAndDataDevolvidoIsNull(cpf);
        } else if (nome != null && !nome.isBlank()) {
            locacoes = locacaoRepository.findByNomeContainingIgnoreCaseAndDataDevolvidoIsNull(nome);
        } else if (email != null && !email.isBlank()) {
            locacoes = locacaoRepository.findByEmailContainingIgnoreCaseAndDataDevolvidoIsNull(email);
        } else if(filme != null && !filme.isBlank()) {
        	locacoes = locacaoRepository.buscarPendentesPorTituloFilme(filme);
        }else {
            locacoes = locacaoRepository.findByDataDevolvidoIsNull();
        }

        return locacoes.stream()
                .map(LocacaoDTO::new)
                .toList();
    }

    public List<LocacaoDTO> buscarDevolvidosFiltrados(String cpf, String nome, String email, String filme) {
        List<Locacao> locacoes;

        if (cpf != null && !cpf.isBlank()) {
            locacoes = locacaoRepository.findByCpfContainingIgnoreCaseAndDataDevolvidoIsNotNull(cpf);
        } else if (nome != null && !nome.isBlank()) {
            locacoes = locacaoRepository.findByNomeContainingIgnoreCaseAndDataDevolvidoIsNotNull(nome);
        } else if (email != null && !email.isBlank()) {
            locacoes = locacaoRepository.findByEmailContainingIgnoreCaseAndDataDevolvidoIsNotNull(email);
        }else if(filme != null && !filme.isBlank()) {
        	locacoes = locacaoRepository.buscarDevolvidosPorTituloFilme(filme);
        }else {
            locacoes = locacaoRepository.findByDataDevolvidoIsNotNull();
        }

        return locacoes.stream()
                .map(LocacaoDTO::new)
                .toList();
    }
    
    public void adicionarLocacao(LocacaoDTO locacaoDTO, List<Integer> exemplarIds) {
        List<Exemplar> exemplares = exemplarService.buscarPorId(exemplarIds);
        exemplares.forEach(exemplar -> {
            if (!exemplar.getAtivo()) {
                throw new IllegalArgumentException("Exemplar não disponível para locação.");
            }
        });
        
        Locacao locacao = locacaoDTO.constroiLocacao(exemplares);
        locacao.setDataLocacao(new Date());
        
        String qrCode = qrCodeService.gerarQrCode(locacao);
        locacao.setQrCode(qrCode);

        exemplares.forEach(exemplar -> {
            Filme filme = exemplar.getFilme();
            filmeService.atualizarExemplares(filme, -1);
        });
        
        locacaoRepository.save(locacao);
    }
    
    public void atualizarLocacao(LocacaoDTO dto, List<Integer> exemplarIds) {
        Locacao locacao = locacaoRepository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException("Locação não encontrada"));
        
        locacao.setNome(dto.nome());
        locacao.setCpf(dto.cpf());
        locacao.setEmail(dto.email());
        locacao.setDataLocacao(new Date());
        locacao.setDataDevolucao(dto.dataDevolucao());

        List<Exemplar> exemplares = exemplarService.buscarPorId(exemplarIds);
        locacao.setExemplares(exemplares);

        exemplares.forEach(exemplar -> {
            Filme filme = exemplar.getFilme();
            filmeService.atualizarExemplares(filme, -1);
        });
        
        locacaoRepository.save(locacao);
    }
 
    public void processarDevolucao(Integer locacaoId, List<Integer> exemplarIds) {
        Locacao locacao = locacaoRepository.findById(locacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Locação não encontrada"));

        List<Exemplar> exemplaresDevolvidos = exemplarService.buscarPorId(exemplarIds);

        exemplaresDevolvidos.forEach(exemplar -> {
            Filme filme = exemplar.getFilme();
            filmeService.atualizarExemplares(filme, 1);
            exemplar.setAtivo(true);
        });

        
        locacao.getExemplares().removeAll(exemplaresDevolvidos);

        if (locacao.getExemplares().isEmpty()) {
            locacao.setDataDevolvido(new Date());
            locacao.setQrCode(null);
        }

        locacaoRepository.save(locacao);
    }
    
    public Locacao buscarPorId(Integer id) {
        return locacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Locação não encontrada"));
    }
    
    public LocacaoDTO buscarDtoPorId(Integer id) {
        Locacao locacao = locacaoRepository.findById(id).orElse(null);
        if (locacao != null) {
            return new LocacaoDTO(locacao);
        }
        return null;
    }

	public void excluirLocacao(Integer id) {
		Locacao locacao = locacaoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Locação não encontrada"));

		if (locacao.getDataDevolvido() == null) {
			throw new ExcecaoPersonalizada("Não é possível excluir uma locação com filmes ainda não devolvidos.");
		}

		locacaoRepository.deleteById(id);
	}
}