package br.com.vinicius.forum.controllers.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.vinicius.forum.entities.Topico;

public class DetalhesTopicoDto {

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
	private String autor;
	private String status;
	private List<RespostaDto> respostas = new ArrayList<>();
	
	public DetalhesTopicoDto() {
	}
	
	public DetalhesTopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
		this.autor = topico.getAutor().getNome();
		this.status = topico.getStatus().toString();
		respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
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

	public String getAutor() {
		return autor;
	}

	public String getStatus() {
		return status;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}

	public TopicoDto converterTopico(Topico topico){
		return new TopicoDto(topico);
	}
}
