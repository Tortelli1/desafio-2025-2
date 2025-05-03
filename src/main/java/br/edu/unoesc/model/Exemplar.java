package br.edu.unoesc.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "EXEMPLAR")
public class Exemplar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "DATA_CADASTRO")
	private Date dataCadastro;
	
	@Column(name = "ATIVO")
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "FILME_ID", nullable = false)
	private Filme filme;
	
	public Exemplar() {
		this.dataCadastro = new Date();
		this.ativo = true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	
	
}
