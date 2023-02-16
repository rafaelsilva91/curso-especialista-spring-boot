package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.domain.entities.Usuario;
import io.github.rafaelsilva91.exceptions.SenhaInvalidaException;
import io.github.rafaelsilva91.rest.dto.CredenciaisDto;
import io.github.rafaelsilva91.rest.dto.TokenDto;
import io.github.rafaelsilva91.security.jwt.JwtService;
import io.github.rafaelsilva91.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save(@RequestBody @Valid Usuario usuario){
        String senhaCripitografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripitografada);
        return usuarioService.save(usuario);
    }


    @PostMapping("/auth")
    public TokenDto autenticar(@RequestBody CredenciaisDto credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha())
                    .build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDto(usuario.getLogin(), token);

        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
