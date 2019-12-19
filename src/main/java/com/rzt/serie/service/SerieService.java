package com.rzt.serie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rzt.serie.model.Serie;
import com.rzt.serie.repository.IExercicio;
import com.rzt.serie.repository.ISerie;

@Service
public class SerieService {
	
	@Autowired
	private ISerie series;
	
	@Autowired
	private IExercicio exercicios;
	
	public Serie salvar(Serie serie) {
		
		serie.setNome(serie.getNome());
		
		serie.getExercicios().forEach(i -> {
			i.setSerie(serie);
			i.setExercicio(exercicios.findById(i.getExercicio().getCodigo()).get());
			i.setNumeroSerie(i.getNumeroSerie());
			i.setNumeroRepeticao(i.getNumeroRepeticao());
			
		});
		
		return series.save(serie);
	}

}
