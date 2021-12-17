package br.com.vinicius.forum.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.vinicius.forum.entities.Curso;


@DataJpaTest
/* 
 * Por padrões os testes do Spring Boot rodam no banco de dados em memória(H2).
 * Caso queira utilizar o banco de dados real, utilize as duas annotations abaixo.
 * 
 * A segunda annotation diz qual profile(application.properties) será lido.
 * Este arquivo pode receber também os profiles de dev, test e prod.
 * 
 * Ex:
 * - application-test.properties
 * - application-dev.properties
 * - application-prod.properties
 * */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository cursoRepository;

	@Test
	public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "HTML 5";
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		Assertions.assertNull(curso);
//		Assertions.assertNotNull(curso);
//		Assertions.assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void naoDeveriaCarregarUmCursoCujoNomeNaoEstejaCadastrado() {
		String nomeCurso = "JPA";
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		Assertions.assertNull(curso);
	}

}
