package com.rzt.serie.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rzt.serie.model.Serie;
import com.rzt.serie.model.SerieExercicio;
import com.rzt.serie.repository.IExercicio;
import com.rzt.serie.repository.ISerie;
import com.rzt.serie.service.SerieService;

@RestController
@RequestMapping("/series")
public class SerieResource {

	@Autowired
	private ISerie series;

	@Autowired
	private IExercicio exercicios;

	@Autowired
	private SerieService serieService;

	@GetMapping
	public ResponseEntity<List<Serie>> listar() {

		List<Serie> listaSerie = series.findAll();

		return ResponseEntity.ok(listaSerie);
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Serie> buscarPorCodigo(@PathVariable("codigo") Long codigo) {

		Optional<Serie> serie = series.findById(codigo);

		if (serie.isPresent())
			return ResponseEntity.ok(serie.get());

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<List<Serie>> buscarPorNome(@PathVariable("nome") String nome) {

		List<Serie> listaSeries = series.findByNomeContainingIgnoreCase(nome);

		if (!listaSeries.isEmpty())
			return ResponseEntity.ok(listaSeries);

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{id}/exercicios")
	public ResponseEntity<List<SerieExercicio>> buscarExercicios(@PathVariable("codigo") Long codigo) {

		Optional<Serie> serie = series.findById(codigo);

		if (serie.isPresent())
			return ResponseEntity.ok(serie.get().getExercicios());

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody @Valid Serie serie) {

		boolean existsByNome = series.existsByNomeIgnoreCase(serie.getNome());

		if (existsByNome)
			return ResponseEntity.badRequest().build();

		else {
			serieService.save(serie);
			
			Serie seriebd = series.save(serie);

			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{codigo}")
					.buildAndExpand(seriebd.getCodigo())
					.toUri();

			return ResponseEntity.created(uri).build();
		}
	}

	@DeleteMapping(value = "/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {

		if (series.existsById(codigo)) {
			series.deleteById(codigo);
			
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
