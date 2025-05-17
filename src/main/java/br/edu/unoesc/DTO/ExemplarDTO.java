package br.edu.unoesc.DTO;

import java.util.Date;

import br.edu.unoesc.model.Exemplar;

public record ExemplarDTO(
		Integer id, 
		Date dataCadastro, 
		Boolean ativo,
		Integer filmeId,
		String filmeTitulo) {
	
	public Exemplar constroiExemplar() {
		Exemplar exemplar = new Exemplar();
        exemplar.setId(this.id);
        exemplar.setDataCadastro(new Date());
        exemplar.setAtivo(this.ativo);
        return exemplar;
	}
	
	public ExemplarDTO(Exemplar exemplar) {
		this(
			exemplar.getId(),
			exemplar.getDataCadastro(),
			exemplar.getAtivo(),
			exemplar.getFilme() != null ? exemplar.getFilme().getId() : null,
			exemplar.getFilme() != null ? exemplar.getFilme().getTitulo() : null);
	}
	
	public static ExemplarDTO configuraExemplar(Exemplar exemplar) {
		return new ExemplarDTO(exemplar);
	}
	
}
