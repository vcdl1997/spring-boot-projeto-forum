package br.com.vinicius.forum.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.vinicius.forum.controllers.dto.DetalhesTopicoDto;
import br.com.vinicius.forum.controllers.dto.TopicoDto;
import br.com.vinicius.forum.controllers.form.AtualizacaoTopicoForm;
import br.com.vinicius.forum.controllers.form.TopicoForm;
import br.com.vinicius.forum.entities.Topico;
import br.com.vinicius.forum.repositories.CursoRepository;
import br.com.vinicius.forum.repositories.TopicoRepository;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	/* Nota:
	 * O Spring utiliza por debaixo dos panos, 
 	 * o framework Jackson, que faz a conversão das respostas para um objeto JSON.
	 * */
	@GetMapping
	public ResponseEntity<List<TopicoDto>> lista(String nomeCurso){
		
		TopicoDto topicoDto = new TopicoDto();
		
		List<Topico> lista = nomeCurso != null ? 
			topicoRepository.findByCursoNome(nomeCurso) : 
			topicoRepository.findAll()		
		;
		
		return ResponseEntity.ok().body(topicoDto.converterListTopico(lista));
	}
	
	/*
	 * Utilizamos a annotation @Valid para validar os campos da nossa classe form,
	 * confome as beans annotation.
	 * 
	 * A annotation @Transactional garante que o metodo irá rodar dentro de uma transação.
	 * */
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(
		@RequestBody @Valid TopicoForm form, 
		UriComponentsBuilder uriBuilder
	){
		Topico topico = form.converter(cursoRepository);
		topico = topicoRepository.save(topico);
		
		/* O padrão de resposta quando criamos um novo recurso é o 201
		 * Com isso obrigatoriámente devemos devolver a uri do recurso que foi criado.
		 * Ex: localhost/topico/1 -> id do registro que foi criado
		 * */
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<DetalhesTopicoDto> obterTopicoPorId(@PathVariable Long id){
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if(!topico.isPresent()) return ResponseEntity.notFound().build();
		
		DetalhesTopicoDto detalhesTopicoDto = new DetalhesTopicoDto(topico.get());
		return ResponseEntity.ok().body(detalhesTopicoDto);
	}
	
	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(
		@PathVariable Long id, 
		@RequestBody @Valid AtualizacaoTopicoForm form
	){
		TopicoDto topicoDto = new TopicoDto(form.atualizar(topicoRepository, id));
		
		return ResponseEntity.ok().body(topicoDto);
	}
	
	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if(!topico.isPresent()) return ResponseEntity.notFound().build();
		
		topicoRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
