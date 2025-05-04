package br.edu.unoesc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unoesc.model.Filme;
import br.edu.unoesc.repository.FilmeRepository;

@Service
public class FilmeService {

	@Autowired
    private FilmeRepository filmeRepository;

	public List<Filme> listarTodos(){
		return filmeRepository.findAll();
	}
	
    public Filme adicionarFilme(Filme filme) {
    	if (filmeRepository.existsByTitulo(filme.getTitulo())) {
            throw new RuntimeException("Filme já cadastrado.");
        }

        filme.setAtivo(true);
        filme.setExemplaresDisponiveis(0L);
        return filmeRepository.save(filme);
    }

    public void atualizarExemplares(Filme filme, int quantidade) {
        filme.setExemplaresDisponiveis(filme.getExemplaresDisponiveis() + quantidade);
        filmeRepository.save(filme);
    }

    public Filme buscarPorId(Integer id) {
        return filmeRepository.findById(id).orElseThrow();
    }
	
}
