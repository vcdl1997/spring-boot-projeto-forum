package br.com.vinicius.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	/*
	 * Sua utilidade é basicamente para permitir que a mensagem seja convertida 
	 * para o idioma do client que fez a solicitação.
	 * */
	@Autowired
	private MessageSource messageSource;
	
	//Erros que serão inteceptados pela nossa controller
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<List<ErroFormularioDto>> handle(MethodArgumentNotValidException exception) {
		List<ErroFormularioDto> lista = new ArrayList<>();
		
		exception.getBindingResult().getFieldErrors().forEach(x -> {
			String mensagem = messageSource.getMessage(x, LocaleContextHolder.getLocale());
			
			lista.add(new ErroFormularioDto(x.getField(), mensagem));
		});
		
		return ResponseEntity.badRequest().body(lista);
	}
	
}
