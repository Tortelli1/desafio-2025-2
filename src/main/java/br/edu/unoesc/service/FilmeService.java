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

	public List<Filme> listarFilmesAtivos() {
		return filmeRepository.findByAtivoTrue();
	}
	
	public List<Filme> listarTodos(){
		return filmeRepository.findAll();
	}
	
    public Filme adicionarFilme(Filme filme) {
    	if (filmeRepository.existsByTitulo(filme.getTitulo())) {
            throw new RuntimeException("Filme já cadastrado.");
        }

        return filmeRepository.save(filme);
    }

    public void atualizarExemplares(Filme filme, int quantidade) {
        filme.setExemplaresDisponiveis(filme.getExemplaresDisponiveis() + quantidade);
        filmeRepository.save(filme);
    }

    
    public void deletarFilme(Integer id) {
		try {
			Filme filme = filmeRepository.findById(id).orElseThrow();
			filmeRepository.delete(filme);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao deletar filme!");
		}
		
	}
    
    
    public Filme buscarPorId(Integer id) {
        return filmeRepository.findById(id).orElseThrow();
    }
	
}
