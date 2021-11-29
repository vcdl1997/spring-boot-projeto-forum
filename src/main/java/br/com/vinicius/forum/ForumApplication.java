package br.com.vinicius.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


/* Com essa anotação nos habilitamos esse suporte, pro Spring pegar da requisição, 
 * dos parametros da url referentes a paginacao e repassar ao Spring Data
 * */
@EnableSpringDataWebSupport
@EnableCaching // Habilita o uso de cache na aplicação.
@EnableSwagger2
@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}
