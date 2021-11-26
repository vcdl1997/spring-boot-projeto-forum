package br.com.vinicius.forum.controllers.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.vinicius.forum.entities.Topico;
import br.com.vinicius.forum.repositories.TopicoRepository;

public class AtualizacaoTopicoForm {
	@NotNull @NotEmpty @Length(min = 3)
	private String titulo;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String mensagem;
	
	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public Topico atualizar(TopicoRepository topicoRepository, Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		
		Topico topico = optional.get();
		
		topico.setTitulo(titulo);
		topico.setMensagem(mensagem);
		
		return topico;
	}
}
