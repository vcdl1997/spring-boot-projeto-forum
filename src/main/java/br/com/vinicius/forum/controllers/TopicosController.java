package br.com.vinicius.forum.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	/* 
	 * O Spring utiliza por debaixo dos panos, 
 	 * o framework Jackson, que faz a conversão das respostas para um objeto JSON.
 	 * 
 	 * Para que a paginação seja feita passamos uma annotation chamada @EnableSpringDataWebSupport, 
 	 * acima do nosso main, e para facilitar a utilização do nosso metodo recebemos um parametro default
 	 * chamado @PageableDefault.
 	 * 
 	 * 
 	 * Para filtrarmos a pagina e a quantidade de registros, devemos utilizar os parametros page e size.
 	 * 
 	 * - page (página) obs: A paginação sempre inicia do 0;
 	 * - size (quantidade de registros por página);
 	 * - sort (
 	 * 		coluna e sentido em que será ordenado a busca, 
 	 * 		pode aparecer mais de uma vez para ordenar multiplos campos
 	 * 	 ).
 	 * 
 	 * Exemplo:
 	 * - http://localhost:8080/topicos?page=0&size=10&sort=id,desc
 	 * 
	 * */
	
	/*
	 * O indicado é que o cache seja utilizado somente em tabelas pouco utilizadas,
	 * no nosso caso de exemplo, 
	 * não seria um bom uso, 
	 * pois constantemente teriamos a publicação de novos tópicos.
	 * */
	@Cacheable(value = "listaDeTopicos")
	@GetMapping
	public ResponseEntity<Page<TopicoDto>> lista(
		@RequestParam(required = false) String nomeCurso, 
		@PageableDefault(
			page = 0,
			size = 10,
			sort = "id", 
			direction = Direction.ASC
		) Pageable paginacao
	){
		TopicoDto topicoDto = new TopicoDto();
		Page<Topico> lista = null;
		
		lista = (nomeCurso != null) ?
			topicoRepository.findByCursoNome(nomeCurso, paginacao) :
			topicoRepository.findAll(paginacao);
		
		Page<TopicoDto> listaDto = topicoDto.converter(lista);
		
		return ResponseEntity.ok().body(listaDto);
	}
	
	/*
	 * Utilizamos a annotation @Valid para validar os campos da nossa classe form,
	 * confome as beans annotation.
	 * 
	 * A annotation @Transactional garante que o metodo irá rodar dentro de uma transação.
	 * */
	@PostMapping
	@Transactional
	// Inválida o cache e força a atualização do mesmo na próxima consulta
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
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
	// Inválida o cache e força a atualização do mesmo na próxima consulta
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(
		@PathVariable Long id, 
		@RequestBody @Valid AtualizacaoTopicoForm form
	){
		TopicoDto topicoDto = new TopicoDto(form.atualizar(topicoRepository, id));
		
		return ResponseEntity.ok().body(topicoDto);
	}
	
	@DeleteMapping(value = "/{id}")
	@Transactional
	// Inválida o cache e força a atualização do mesmo na próxima consulta
	@CacheEvict(value = "listaDeTopicos", allEntries = true) 
	public ResponseEntity<?> remover(@PathVariable Long id){
		
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if(!topico.isPresent()) return ResponseEntity.notFound().build();
		
		topicoRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
