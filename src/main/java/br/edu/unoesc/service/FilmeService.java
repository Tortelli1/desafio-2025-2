package br.edu.unoesc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.DTO.FilmeDTO;
import br.edu.unoesc.excecoes.ExcecaoPersonalizada;
import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.model.Filme;
import br.edu.unoesc.repository.ExemplarRepository;
import br.edu.unoesc.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
    private FilmeRepository filmeRepository;
	
	@Autowired
	private ExemplarRepository exemplarRepository;

	public List<FilmeDTO> listarFilmesAtivos() {
		return filmeRepository.findByAtivoTrue()
				.stream()
				.map(filme -> FilmeDTO.configuraFilme(filme))
				.collect(Collectors.toList());
	}
	
    public List<FilmeDTO> listarTodos() { 
        List<Filme> filmes = filmeRepository.findAll();
        return filmes.stream()
                     .map(filme -> FilmeDTO.configuraFilme(filme))
                     .collect(Collectors.toList());
    }
	
    public Filme adicionarFilme(Filme filme) {
    	if (filmeRepository.existsByTitulo(filme.getTitulo())) {
            throw new RuntimeException("Filme já cadastrado.");
        }

    	filme.setExemplaresDisponiveis(0L);
        return filmeRepository.save(filme);
    }
    
    public void atualizarFilme(FilmeDTO dto) {
        Filme existente = filmeRepository.findById(dto.id()).orElseThrow();
        existente.setResumo(dto.resumo());
        existente.setAtivo(dto.ativo());
        filmeRepository.save(existente);
    }
    
    public void atualizarExemplares(Filme filme, int quantidade) {
        filme.setExemplaresDisponiveis(filme.getExemplaresDisponiveis() + quantidade);
        filmeRepository.save(filme);
    }
    
	public void deletarFilme(Integer id) {
        if (!filmeRepository.existsById(id)) {
        	throw new RuntimeException("Filme não encontrado!");
        }
            
		List<Exemplar> exemplares = exemplarRepository.findByFilmeId(id);
		if (!exemplares.isEmpty()) {
			throw new ExcecaoPersonalizada("Não é possível excluir o filme. Existem exemplares associados a ele.");
		}
    }

    public FilmeDTO buscarDtoPorId(Integer id) {
        Filme filme = filmeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        return FilmeDTO.configuraFilme(filme);
    }
    
    public Filme buscarPorId(Integer id) {
        return filmeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
    }
	
}
