package br.com.vinicius.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vinicius.forum.entities.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
	Curso findByNome(String nome);
}
