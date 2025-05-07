package br.edu.unoesc.DTO;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import br.edu.unoesc.model.Filme;

public record FilmeDTO(
		Integer id,
		Boolean ativo, 
		Long exemplaresDisponiveis,
		String titulo,
		String resumo,
		String pontuacao,
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		Date lancamento
		) {
	
	public Filme constroiFilme() {
	    Filme filme = new Filme();
	    filme.setId(this.id);
	    filme.setAtivo(this.ativo);
	    filme.setExemplaresDisponiveis(this.exemplaresDisponiveis);
	    filme.setTitulo(this.titulo);
	    filme.setResumo(this.resumo);
	    filme.setPontuacao(this.pontuacao);
	    filme.setLancamento(this.lancamento);
	    return filme;
	}
	
	public static FilmeDTO configuraFilme(Filme filme) {
	    return new FilmeDTO(
	        filme.getId(),
	        filme.getAtivo(),
	        filme.getExemplaresDisponiveis(),
	        filme.getTitulo(),
	        filme.getResumo(),
	        filme.getPontuacao(),
	        filme.getLancamento()
	    );
	}
	
}
