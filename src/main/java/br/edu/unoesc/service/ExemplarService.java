package br.edu.unoesc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Exemplar salvar(Exemplar exemplar, Filme filme) {
		if (!Boolean.TRUE.equals(filme.getAtivo())) {
			throw new RuntimeException("Filme inativo.");
		}
		exemplar.setDataCadastro(new Date());
		exemplar.setAtivo(true);
		exemplar.setFilme(filme);

		Exemplar salvo = exemplarRepository.save(exemplar);
		filmeService.atualizarExemplares(filme, 1);
		return salvo;
	}

	public void inativar(Integer id) {
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
	
}
