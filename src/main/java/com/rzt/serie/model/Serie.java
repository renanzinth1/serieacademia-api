package com.rzt.serie.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tab_series")
public class Serie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_SERIE")
	@SequenceGenerator(name = "CODIGO_SERIE", sequenceName = "SEQ_CODIGO_SERIE", allocationSize = 1)
	private Long codigo;
	
	@Column
	@NotEmpty
	private String nome;
	
	@JsonIgnoreProperties("serie")
	@OneToMany(mappedBy = "serie", targetEntity = SerieExercicio.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SerieExercicio> exercicios;

	public Serie() {
		super();
	}

	public Serie(Long codigo, @NotEmpty String nome, List<SerieExercicio> exercicios) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.exercicios = exercicios;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long id) {
		this.codigo = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<SerieExercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<SerieExercicio> exercicios) {
		this.exercicios = exercicios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serie other = (Serie) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
