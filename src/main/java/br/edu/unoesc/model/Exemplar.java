package br.edu.unoesc.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EXEMPLAR")
@Getter
@Setter
@NoArgsConstructor
public class Exemplar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "DATA_CADASTRO")
	private Date dataCadastro;
	
	@Column(name = "ATIVO")
	private Boolean ativo;
	
}
