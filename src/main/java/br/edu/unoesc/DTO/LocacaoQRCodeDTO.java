package br.edu.unoesc.DTO;

import java.util.Date;
import java.util.List;

public record LocacaoQRCodeDTO(
	    Integer id, 
	    String nome, 
	    String cpf, 
	    String email, 
	    String telefone, 
	    Date dataLocacao, 
	    Date dataDevolucao, 
	    Date dataDevolvido, 
	    String qrCodeBase64, 
	    List<FilmeDTO> listaFilmes,
	    Boolean locacaoDevolvida
	) {

}
