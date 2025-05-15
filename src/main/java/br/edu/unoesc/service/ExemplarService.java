package br.edu.unoesc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.DTO.ExemplarDTO;
import br.edu.unoesc.excecoes.ExcecaoPersonalizada;
import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.model.Filme;
import br.edu.unoesc.repository.ExemplarRepository;
import br.edu.unoesc.repository.LocacaoRepository;

@Service
public class ExemplarService {

	@Autowired
	private ExemplarRepository exemplarRepository;

	@Autowired
	private FilmeService filmeService;

	@Autowired
	private LocacaoRepository locacaoRepository;	
	
    public List<ExemplarDTO> listarTodos() {
        List<Exemplar> exemplares = exemplarRepository.findAll();
        return exemplares.stream()
                         .map(exemplar -> new ExemplarDTO(exemplar))
                         .collect(Collectors.toList());
    }
	
	public ExemplarDTO adicionarExemplar(ExemplarDTO exemplarDTO) {
		Filme filme = filmeService.buscarPorId(exemplarDTO.filmeId());

		if (!filme.getAtivo()) {
			throw new RuntimeException("Filme inativo.");
		}

		Exemplar exemplar = exemplarDTO.constroiExemplar();
		exemplar.setFilme(filme);
		
		Exemplar salvo = exemplarRepository.save(exemplar);
		filmeService.atualizarExemplares(filme, 1);
		
		return new ExemplarDTO(salvo);
	}

	public ExemplarDTO inativarExemplar(ExemplarDTO exemplarDTO) {
	    Exemplar exemplar = exemplarRepository.findById(exemplarDTO.id())
	            .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

	    if (exemplar.getAtivo() && locacaoRepository.existsByExemplares_IdAndDataDevolvidoIsNull(exemplar.getId())) {
	        throw new ExcecaoPersonalizada("Não é possível inativar o exemplar. Ele está atualmente alugado.");
	    }
	    
	    if (exemplar.getAtivo()) {
	        Filme filme = exemplar.getFilme();
	        filmeService.atualizarExemplares(filme, -1);
	        exemplar.setAtivo(false);
	        exemplarRepository.save(exemplar);
	    } else {
	    	Filme filme = exemplar.getFilme();
	    	exemplar.setAtivo(true);
	    	filmeService.atualizarExemplares(filme, 1);
	    	exemplarRepository.save(exemplar);
	    }
	    return new ExemplarDTO(exemplar);
	}
	
    public void deletarExemplar(Integer id) {
        Exemplar exemplar = exemplarRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

        if (!exemplar.getAtivo()) {
            throw new ExcecaoPersonalizada("Exemplar inativo não pode ser excluído.");
        }

        boolean estaAlugado = locacaoRepository.existsByExemplares_IdAndDataDevolvidoIsNull(id);
        if (estaAlugado) {
            throw new RuntimeException("Exemplar está atualmente alugado e não pode ser excluído.");
        }

        exemplarRepository.deleteById(id);
        if(exemplar.getAtivo()) {
        	filmeService.atualizarExemplares(exemplar.getFilme(), -1);
        }
      
    }


    public Exemplar buscarPorId(Integer id) {
        return exemplarRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));
    }
    
    public List<ExemplarDTO> listarExemplaresPorFilme(Integer filmeId) {
        filmeService.buscarPorId(filmeId);
        List<Exemplar> exemplaresAtivos = exemplarRepository.findByFilmeId(filmeId);
        
        return exemplaresAtivos.stream()
                               .map(exemplar -> new ExemplarDTO(exemplar))
                               .collect(Collectors.toList());
    }
    
    public List<ExemplarDTO> listarExemplaresAtivos() {
        List<Exemplar> exemplares = exemplarRepository.findByAtivoTrue();
        return exemplares.stream()
                         .map(ExemplarDTO::new)
                         .collect(Collectors.toList());
    }
    
    public List<Exemplar> buscarPorId(List<Integer> idsExemplares) {
        return exemplarRepository.findAllById(idsExemplares);
    }
    
}
