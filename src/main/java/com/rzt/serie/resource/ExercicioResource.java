package com.rzt.serie.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rzt.serie.model.Categoria;
import com.rzt.serie.model.Exercicio;
import com.rzt.serie.repository.ICategoria;
import com.rzt.serie.repository.IExercicio;

@RestController
@RequestMapping("/exercicios")
public class ExercicioResource {

	@Autowired
	private IExercicio exercicios;
	
	@Autowired
	private ICategoria categorias;

	@GetMapping
	public ResponseEntity<List<Exercicio>> listar() {

		List<Exercicio> listaExercicios = exercicios.findAll();

		return ResponseEntity.ok(listaExercicios);
	}
	
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Exercicio> buscarPorCodigo(@PathVariable("codigo") Long codigo) {
		
		Optional<Exercicio> exercicio = exercicios.findById(codigo);
		
		if (exercicio.isPresent())
			return ResponseEntity.ok(exercicio.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<List<Exercicio>> buscarPorNome(@PathVariable("nome") String nome) {
		
		List<Exercicio> listaExercicios = exercicios.findAllByNomeContainingIgnoreCaseOrderByNomeAsc(nome);
		
		if (!listaExercicios.isEmpty())
			return ResponseEntity.ok(listaExercicios);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/categoria/{nome}")
	public ResponseEntity<List<Exercicio>> buscarPorCategoria(@PathVariable("nome") String nome) {
		
		Optional<Categoria> categoria = categorias.findByNomeContainingIgnoreCase(nome);
		
		if(categoria.isPresent())
			return ResponseEntity.ok(categoria.get().getExercicios());
		
		return ResponseEntity.notFound().build();
		
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Exercicio exercicio) {

		boolean existsByNome = exercicios.existsByNomeContainingIgnoreCase(exercicio.getNome());

		if (existsByNome)
			return ResponseEntity.badRequest().build();

		else {
			exercicio = exercicios.save(exercicio);

			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{codigo}")
					.buildAndExpand(exercicio.getCodigo())
					.toUri();
			
			return ResponseEntity.created(uri).build();
		}

	}
}
