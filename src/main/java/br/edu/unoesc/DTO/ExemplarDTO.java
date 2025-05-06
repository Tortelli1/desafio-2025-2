package br.edu.unoesc.DTO;

import java.util.Date;

import br.edu.unoesc.model.Exemplar;

public record ExemplarDTO(
		Integer id, 
		FilmeDTO filme, 
		Date dataCadastro, 
		Boolean ativo) {
	
	public Exemplar constroiExemplar() {
		Exemplar exemplar = new Exemplar();
        exemplar.setId(this.id);
        exemplar.setFilme(this.filme.constroiFilme());
        exemplar.setDataCadastro(this.dataCadastro);
        exemplar.setAtivo(this.ativo);
        return exemplar;
	}
	
	
}
