package br.edu.unoesc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LOCACAO")
@Getter
@Setter
@NoArgsConstructor
public class Locacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "CPF")
	private String cpf;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "TELEFONE")
	private String telefone;
	
	@Column(name= "DATA_ALOCACAO")
	private Date dataLocacao;
	
	@Column(name = "DATA_DEVOLUCAO")
	private Date dataDevolucao;
	
	@Column(name = "DATA_DEVOLVIDO")
	private Date dataDevolvido;
	
	@ManyToMany
	@JoinTable(
	    name = "LOCACAO_EXEMPLAR",
	    joinColumns = @JoinColumn(name = "LOCACAO_ID"),
	    inverseJoinColumns = @JoinColumn(name = "EXEMPLAR_ID")
	)
	private List<Exemplar> exemplares = new ArrayList<>();
	
}
