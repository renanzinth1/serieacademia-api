package com.rzt.serie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rzt.serie.model.Exercicio;

public interface IExercicio extends JpaRepository<Exercicio, Long> {
	
	public boolean existsByNomeContainingIgnoreCase(String nome);
	
	public List<Exercicio> findAllByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

}
