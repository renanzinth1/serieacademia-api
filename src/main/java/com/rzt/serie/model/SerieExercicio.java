package com.rzt.serie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tab_serie_exercicios")
public class SerieExercicio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODIGO_SERIE_EXERCICIO")
	@SequenceGenerator(name = "CODIGO_SERIE_EXERCICIO", sequenceName = "SEQ_CODIGO_SERIE_EXERCICIO", allocationSize = 1)
	private Long codigo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_serie_fk")
	private Serie serie;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_exercicio_fk")
	private Exercicio exercicio;
	
	@NotNull
	@Min(value = 1)
	@Column(name = "numero_serie")
	private Long numeroSerie;
	
	@NotNull
	@Min(value = 1)
	@Column(name = "numero_repeticao")
	private Long numeroRepeticao;

	public SerieExercicio() {
		super();
	}

	public SerieExercicio(Long codigo, @NotNull Serie serie, @NotNull Exercicio exercicio,
			@NotNull @Min(1) Long numeroSerie, @NotNull @Min(1) Long numeroRepeticao) {
		super();
		this.codigo = codigo;
		this.serie = serie;
		this.exercicio = exercicio;
		this.numeroSerie = numeroSerie;
		this.numeroRepeticao = numeroRepeticao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public Exercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}

	public Long getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(Long numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public Long getNumeroRepeticao() {
		return numeroRepeticao;
	}

	public void setNumeroRepeticao(Long numeroRepeticao) {
		this.numeroRepeticao = numeroRepeticao;
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
		SerieExercicio other = (SerieExercicio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
