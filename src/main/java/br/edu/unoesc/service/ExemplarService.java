package br.edu.unoesc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.DTO.ExemplarDTO;
import br.edu.unoesc.DTO.FilmeDTO;
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

	public ExemplarDTO adicionarExemplar(ExemplarDTO exemplarDTO, FilmeDTO filmeDTO) {
		 Filme filme = filmeDTO.constroiFilme();
	        if (!Boolean.TRUE.equals(filme.getAtivo())) {
	            throw new RuntimeException("Filme inativo.");
	        }

	        Exemplar exemplar = exemplarDTO.constroiExemplar();
	        exemplar.setFilme(filme);
	        Exemplar salvo = exemplarRepository.save(exemplar);
	        filmeService.atualizarExemplares(filme, 1);
	        return new ExemplarDTO(salvo);
	    }

	public void deletarExemplar(Integer id) {
		Exemplar exemplar = exemplarRepository.findById(id).orElseThrow();
		if (!exemplar.getAtivo())
			return;

		boolean estaAlugado = locacaoRepository.existsByExemplares_IdAndDataDevolvidoIsNull(id);
		if (estaAlugado) {
			throw new RuntimeException("Exemplar está alugado.");
		}

		exemplar.setAtivo(false);
		exemplarRepository.save(exemplar);
		filmeService.atualizarExemplares(exemplar.getFilme(), -1);
	}

	public List<Exemplar> listarTodos() {
		return exemplarRepository.findAll();
	}

}
