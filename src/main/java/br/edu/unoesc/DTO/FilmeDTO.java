package br.edu.unoesc.DTO;

import java.util.Date;
import java.util.List;

public record FilmeDTO(Integer id, Boolean ativo, Long exemplaresDisponiveis, String titulo, String resumo, String pontuacao, Date lancamento, List<ExemplarDTO> exemplares) {


	
}
