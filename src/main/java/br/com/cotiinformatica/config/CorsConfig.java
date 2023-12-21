package br.com.cotiinformatica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Classe para configurar as permissões de CORS da API
 * definindo quais projetos (domínios) poderão fazer
 * requisições para a nossa API
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

	/*
	 * Método para detalhar as permissões da API
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
			.allowedOrigins("http://localhost:4200")
			.allowedMethods("POST", "PUT", "DELETE", "GET")
			.allowedHeaders("*");
	}
	
}
