package br.com.cotiinformatica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	@Query("select u from Usuario u where u.email = :email")
	Usuario find(@Param("email") String email);

	@Query("select u from Usuario u where u.email = :email and u.senha = :senha")
	Usuario find(@Param("email") String email, @Param("senha") String senha);
}
