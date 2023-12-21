package br.com.cotiinformatica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.requests.AutenticarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.requests.CriarUsuarioRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JavaProjetoUsuariosApiApplicationTests {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//atributos estáticos
	private static String email;
	private static String senha;
	
	@Test
	@Order(1)
	public void criarUsuarioTest() throws Exception {
		
		CriarUsuarioRequestDto request = new CriarUsuarioRequestDto();
		Faker faker = new Faker();
		
		request.setNome(faker.name().fullName());
		request.setEmail(faker.internet().emailAddress());
		request.setSenha("@Teste123");
		
		mockMvc.perform(post("/api/usuarios/criar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());
		
		email = request.getEmail();
		senha = request.getSenha();
	}
	
	@Test
	@Order(2)
	public void criarUsuarioJaExistenteTest() throws Exception {
		
		CriarUsuarioRequestDto request = new CriarUsuarioRequestDto();
		Faker faker = new Faker();
		
		request.setNome(faker.name().fullName());
		request.setEmail(email);
		request.setSenha(senha);
		
		mockMvc.perform(post("/api/usuarios/criar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(3)
	public void autenticarUsuarioTest() throws Exception {
		
		AutenticarUsuarioRequestDto request = new AutenticarUsuarioRequestDto();
		request.setEmail(email);
		request.setSenha(senha);
		
		mockMvc.perform(post("/api/usuarios/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());		
	}
	
	@Test
	@Order(4)
	public void acessoNegadoTest() throws Exception {
		
		AutenticarUsuarioRequestDto request = new AutenticarUsuarioRequestDto();
		Faker faker = new Faker();
		
		request.setEmail(faker.internet().emailAddress());
		request.setSenha("@Teste321");
		
		mockMvc.perform(post("/api/usuarios/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());		
	}
}
