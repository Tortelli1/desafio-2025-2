package br.edu.unoesc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "LOCACAO")
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
	
	@Column(name = "QR_CODE")
	private String qrCode;
	
	@ManyToMany
	@JoinTable(
	    name = "LOCACAO_EXEMPLAR",
	    joinColumns = @JoinColumn(name = "LOCACAO_ID"),
	    inverseJoinColumns = @JoinColumn(name = "EXEMPLAR_ID")
	)
	private List<Exemplar> exemplares = new ArrayList<>();
	
	public Locacao() {
		this.dataLocacao = new Date();
		this.dataDevolucao = new Date();
		this.dataDevolvido = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getDataDevolvido() {
		return dataDevolvido;
	}

	public void setDataDevolvido(Date dataDevolvido) {
		this.dataDevolvido = dataDevolvido;
	}
	
	public List<Exemplar> getExemplares() {
		return exemplares;
	}

	public void setExemplares(List<Exemplar> exemplares) {
		this.exemplares = exemplares;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	

}
