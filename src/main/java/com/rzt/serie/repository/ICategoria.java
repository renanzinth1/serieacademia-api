package com.rzt.serie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rzt.serie.model.Categoria;

public interface ICategoria extends JpaRepository<Categoria, Long> {
	
	public boolean existsById(Long id);
	
	public boolean existsByNomeIgnoreCase(String nome);
	
	public Optional<Categoria> findByNomeContainingIgnoreCase(String nome);

}
