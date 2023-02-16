package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.domain.entities.Usuario;
import io.github.rafaelsilva91.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save(@RequestBody @Valid Usuario usuario){
        String senhaCripitografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripitografada);
        return usuarioService.save(usuario);
    }
}
