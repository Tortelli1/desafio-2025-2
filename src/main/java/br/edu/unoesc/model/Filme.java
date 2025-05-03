package br.edu.unoesc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILME")
@NoArgsConstructor
@AllArgsConstructor
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
	
	@OneToMany(mappedBy = "filme", cascade = CascadeType.ALL)
	private List<Exemplar> exemplares = new ArrayList<>();
	
	public Filme(String titulo, String resumo, String pontuacao, Date lancamento) {
		this.titulo = titulo;
		this.resumo = resumo;
		this.pontuacao = pontuacao;
		this.lancamento = lancamento;
		this.ativo = true;
		this.exemplaresDisponiveis = 0L;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Long getExemplaresDisponiveis() {
		return exemplaresDisponiveis;
	}

	public void setExemplaresDisponiveis(Long exemplaresDisponiveis) {
		this.exemplaresDisponiveis = exemplaresDisponiveis;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public String getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(String pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Date getLancamento() {
		return lancamento;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}

	public List<Exemplar> getExemplares() {
		return exemplares;
	}

	public void setExemplares(List<Exemplar> exemplares) {
		this.exemplares = exemplares;
	}

}
