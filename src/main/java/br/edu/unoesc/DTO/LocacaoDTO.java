package br.edu.unoesc.DTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.unoesc.model.Exemplar;
import br.edu.unoesc.model.Locacao;

public record LocacaoDTO(
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
	
    public Locacao constroiLocacao(List<Exemplar> exemplares) {
        Locacao locacao = new Locacao();
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
