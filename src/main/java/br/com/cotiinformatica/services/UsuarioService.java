package br.com.cotiinformatica.services;

import java.time.Instant;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.requests.AutenticarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.requests.CriarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.responses.AutenticarUsuarioResponseDto;
import br.com.cotiinformatica.dtos.responses.CriarUsuarioResponseDto;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.helpers.Sha1CryptoHelper;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.JwtBearerSecurity;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	JwtBearerSecurity jwtBearerSecurity;
	
	public CriarUsuarioResponseDto criarUsuario(CriarUsuarioRequestDto request) {
		
		//verificar se já existe um usuário cadastrado com o email informado
		if(usuarioRepository.find(request.getEmail()) != null) {
			throw new IllegalArgumentException("O email informado já está cadastrado. Tente outro.");
		}
		
		//capturar os dados do usuário
		Usuario usuario = modelMapper.map(request, Usuario.class);
		usuario.setIdUsuario(UUID.randomUUID());
		usuario.setSenha(Sha1CryptoHelper.hashString(request.getSenha()));
		
		//gravar o usuário no banco de dados
		usuarioRepository.save(usuario);
		
		CriarUsuarioResponseDto response = modelMapper.map(usuario, CriarUsuarioResponseDto.class);
		response.setDataHoraCadastro(Instant.now());		
		return response;
	}
	
	public AutenticarUsuarioResponseDto autenticar(AutenticarUsuarioRequestDto request) {
		
		//buscar 1 usuário no banco de dados através do email e da senha
		Usuario usuario = usuarioRepository.find
				(request.getEmail(), Sha1CryptoHelper.hashString(request.getSenha()));
		
		//verificar se o usuário não foi encontrado
		if(usuario == null) {
			throw new IllegalArgumentException("Acesso negado. Usuário não encontrado.");
		}
		
		//retornar os dados do usuário autenticado
		AutenticarUsuarioResponseDto response = modelMapper.map(usuario, AutenticarUsuarioResponseDto.class);
		response.setAccessToken(jwtBearerSecurity.createToken(usuario.getEmail()));
		response.setDataHoraAcesso(Instant.now());
		response.setDataHoraExpiracao(jwtBearerSecurity.getExpiration().toInstant());
		return response;
	}
}













