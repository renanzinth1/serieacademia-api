package com.rzt.serie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rzt.serie.model.Serie;

public interface ISerie extends JpaRepository<Serie, Long> {

	public List<Serie> findByNomeContainingIgnoreCase(String nome);

	public boolean existsByNomeIgnoreCase(String nome);

}
