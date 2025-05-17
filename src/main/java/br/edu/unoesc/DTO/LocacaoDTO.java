package br.edu.unoesc.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.model.Locacao;

public record LocacaoDTO(
		Integer id,
		List<ExemplarDTO> listaExemplares, 
	    String nome, 
	    String cpf, 
	    String email, 
	    String telefone,
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    Date dataLocacao,
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    Date dataDevolucao,
	    Date dataDevolvido,
	    String qrCode
		) {
	
	public List<Integer> getExemplarIds() {
	    if (listaExemplares == null) {
	        return new ArrayList<>();
	    }
	    return listaExemplares.stream()
	        .map(ExemplarDTO::id) 
	        .collect(Collectors.toList());
	}

    public Locacao constroiLocacao(List<Exemplar> exemplares) {
        Locacao locacao = new Locacao();
        locacao.setId(this.id);
        locacao.setNome(this.nome);
        locacao.setCpf(this.cpf);
        locacao.setEmail(this.email);
        locacao.setTelefone(this.telefone);
        locacao.setDataLocacao(this.dataLocacao);
        locacao.setDataDevolucao(this.dataDevolucao);
        locacao.setDataDevolvido(this.dataDevolvido);
        locacao.setExemplares(exemplares);
        return locacao;
    }
	
    public LocacaoDTO(Locacao locacao) {
        this(
            locacao.getId(),
            locacao.getExemplares().stream()
                .map(ExemplarDTO::configuraExemplar)
                .collect(Collectors.toList()), 
            locacao.getNome(),
            locacao.getCpf(),
            locacao.getEmail(),
            locacao.getTelefone(),
            locacao.getDataLocacao(),
            locacao.getDataDevolucao(),
            locacao.getDataDevolvido(),
            locacao.getQrCode()
        );
    }    
}
