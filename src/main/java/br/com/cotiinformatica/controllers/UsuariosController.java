package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.requests.AutenticarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.requests.CriarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.responses.AutenticarUsuarioResponseDto;
import br.com.cotiinformatica.dtos.responses.CriarUsuarioResponseDto;
import br.com.cotiinformatica.services.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuariosController {
	
	@Autowired
	UsuarioService usuarioService;

	@PostMapping("criar")
	public CriarUsuarioResponseDto criarUsuario(@RequestBody @Valid CriarUsuarioRequestDto request) {
		return usuarioService.criarUsuario(request);
	}
	
	@PostMapping("autenticar")
	public AutenticarUsuarioResponseDto autenticarUsuario(@RequestBody @Valid AutenticarUsuarioRequestDto request) {
		return usuarioService.autenticar(request);
	}
}
