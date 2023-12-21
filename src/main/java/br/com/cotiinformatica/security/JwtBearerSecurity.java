package br.com.cotiinformatica.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtBearerSecurity {

	/*
	 * Lendo o parametro jwt.secret
	 * mapeado no /application.properties
	 */
	@Value("${jwt.secret}")
	String jwtSecret;
	
	/*
	 * Lendo o parametro jwt.expiration
	 * mapeado no /application.properties
	 */
	@Value("${jwt.expiration}")
	String jwtExpiration;
	
	/*
	 * Método para calcular o tempo de expiração do TOKEN
	 */
	public Date getExpiration() {
		Date dataAtual = new Date();
		return new Date(dataAtual.getTime() + Integer.parseInt(jwtExpiration) * 1000);
	}
	
	/*
	 * Método para gerar o TOKEN JWT
	 */
	public String createToken(String emailUsuario) {		
		return Jwts.builder()
				.setSubject(emailUsuario) //identificação do usuário
				.setIssuedAt(new Date()) //data de geração do token
				.setExpiration(getExpiration()) //data de expiração do token
				.signWith(SignatureAlgorithm.HS256, jwtSecret) //chave antifalsificação
				.compact();				
	}
}





