package br.edu.unoesc.model;

import java.util.Date;
import java.util.List;

import br.edu.unoesc.DTO.FilmeDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "FILME")
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
	
	@OneToMany(mappedBy = "filme")
	private List<Exemplar> exemplares;
	
	public Filme(FilmeDTO filmeDTO) {
		this.id = filmeDTO.id();
		this.ativo = filmeDTO.ativo();
		this.exemplaresDisponiveis = filmeDTO.exemplaresDisponiveis();
		this.titulo = filmeDTO.titulo();
		this.resumo = filmeDTO.resumo();
		this.pontuacao = filmeDTO.pontuacao();
		this.lancamento = filmeDTO.lancamento();
	}

	public Filme() {
		
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
