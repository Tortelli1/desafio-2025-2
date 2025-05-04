package br.edu.unoesc.DTO;

import java.util.Date;
import java.util.List;

public record LocacaoDTO(List<FilmeDTO> listaFilmes, String nome, String cpf, String email, String telefone, Date dataLocacao, Date dataDevolucao, Date dataDevolvido) {
	

}
