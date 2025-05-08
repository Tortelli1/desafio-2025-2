package br.edu.unoesc.DTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.unoesc.model.Locacao;

public record LocacaoDTO(
		List<ExemplarDTO> listaExemplares, 
	    String nome, 
	    String cpf, 
	    String email, 
	    String telefone, 
	    Date dataLocacao, 
	    Date dataDevolucao, 
	    Date dataDevolvido
		) {
	
	public LocacaoDTO(Locacao locacao) {
        this(
            locacao.getExemplares().stream()
                .map(ExemplarDTO::configuraExemplar)
                .collect(Collectors.toList()), 
            locacao.getNome(),
            locacao.getCpf(),
            locacao.getEmail(),
            locacao.getTelefone(),
            locacao.getDataLocacao(),
            locacao.getDataDevolucao(),
            locacao.getDataDevolvido()
        );
    }

}
