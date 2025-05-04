package br.edu.unoesc.DTO;

import java.util.Date;

public record ExemplarDTO(Integer id, FilmeDTO filme, Date dataCadastro, Boolean ativo) {
	

}
