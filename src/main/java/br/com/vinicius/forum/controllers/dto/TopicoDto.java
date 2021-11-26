package br.com.vinicius.forum.controllers.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.vinicius.forum.entities.Topico;

public class TopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	
	@JsonFormat(
		shape = JsonFormat.Shape.STRING,
		pattern = "dd/MM/yyyy HH:mm:ss",
		locale = "pt-BR",
		timezone = "Brazil/East"
	)
	private LocalDateTime dataCriacao;
	
	public TopicoDto() {
	}
	
	public TopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	public List<TopicoDto> converterListTopico(List<Topico> topicos){
		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	}
}
