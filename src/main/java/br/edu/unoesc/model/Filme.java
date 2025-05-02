package br.edu.unoesc.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FILME")
@Getter
@Setter
@NoArgsConstructor
public class Filme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ATIVO")
	private Boolean ativo;
	
	@Column(name = "EXEMPLARES_DISPONIVEIS")
	private Long exemplaresDisponiveis;
	
	@Column(name = "TITULO")
	private String titulo;
	
	@Column(name = "RESUMO")
	private String resumo;
	
	@Column(name = "PONTUACAO")
	private String pontuacao;
	
	@Column(name = "LANCAMENTO")
	private Date lancamento;
	
}
