package br.com.vinicius.forum.controllers.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.vinicius.forum.entities.Resposta;

public class RespostaDto {
	private Long id;
	private String mensagem;
	
	@JsonFormat(
		shape = JsonFormat.Shape.STRING,
		pattern = "dd/MM/yyyy HH:mm:ss",
		locale = "pt-BR",
		timezone = "Brazil/East"
	)
	private LocalDateTime dataCriacao;
	private String autor;
	
	public RespostaDto(Resposta reposta) {
		this.id = reposta.getId();
		this.mensagem = reposta.getMensagem();
		this.dataCriacao = reposta.getDataCriacao();
		this.autor = reposta.getAutor().getNome();
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public String getAutor() {
		return autor;
	}
}
