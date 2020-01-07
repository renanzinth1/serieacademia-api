package com.rzt.serie.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private ICategoria categorias;

	@Autowired
	private IExercicio exercicios;

	@GetMapping
	public ResponseEntity<List<Categoria>> listar() {

		List<Categoria> Listacategorias = categorias.findAll();

		return ResponseEntity.ok(Listacategorias);
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable("codigo") Long codigo) {

		Optional<Categoria> categoria = categorias.findById(codigo);

		if (categoria.isPresent())
			return ResponseEntity.ok(categoria.get());

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/nome/{nome}")
	public ResponseEntity<Categoria> buscarPorNome(@PathVariable("nome") String nome) {

		Optional<Categoria> categoria = categorias.findByNomeContainingIgnoreCase(nome);

		if (categoria.isPresent())
			return ResponseEntity.ok(categoria.get());

		return ResponseEntity.notFound().build();

	}

	@GetMapping(value = "/{codigo}/exercicios")
	public ResponseEntity<List<Exercicio>> buscarExercicios(@PathVariable("codigo") Long codigo) {

		if (categorias.existsById(codigo)) {
			Optional<Categoria> categoria = categorias.findById(codigo);

			return ResponseEntity.ok(categoria.get().getExercicios());
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{codigoCategoria}/exercicios/{codigoExercicio}")
	public ResponseEntity<Exercicio> buscarExercicio(@PathVariable("codigoCategoria") Long codigoCategoria,
			@PathVariable("codigoExercicio") Long codigoExercicio) {

		if (categorias.existsById(codigoCategoria)) {
			Optional<Categoria> categoria = categorias.findById(codigoCategoria);

			Optional<Exercicio> exercicio = exercicios.findById(codigoExercicio);

			if (exercicio.isPresent()) {
				if (categoria.get().getExercicios().contains(exercicio.get()))
					return ResponseEntity.ok(exercicio.get());
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Categoria categoria) {

		boolean existsByNome = categorias.existsByNomeIgnoreCase(categoria.getNome());

		if (existsByNome)
			return ResponseEntity.badRequest().build();

		else {
			categoria = categorias.save(categoria);

			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{codigo}")
					.buildAndExpand(categoria.getCodigo())
					.toUri();

			return ResponseEntity.created(uri).build();
		}
	}

	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Categoria> editar(@PathVariable("codigo") Long codigo, @RequestBody Categoria categoria) {

		Optional<Categoria> categoriaComMesmoNome = categorias.findByNomeContainingIgnoreCase(categoria.getNome());

		if (categoriaComMesmoNome.isPresent())
			return ResponseEntity.badRequest().build();

		if (categorias.existsById(codigo)) {
			categoria.setCodigo(codigo);
			categorias.save(categoria);

			return ResponseEntity.accepted().body(categoria);
		}
		
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(value = "/{codigo}")
	public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {

		if (categorias.existsById(codigo)) {
			categorias.deleteById(codigo);

			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
