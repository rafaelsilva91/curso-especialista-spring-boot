package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.exceptions.PedidoNaoEncontradoExceptions;
import io.github.rafaelsilva91.exceptions.RegraNegocioExceptions;
import io.github.rafaelsilva91.rest.ApiErros;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleRegraNegocioException(RegraNegocioExceptions ex){
        String erro = ex.getMessage();
        return new ApiErros(erro);
    }

    @ExceptionHandler(PedidoNaoEncontradoExceptions.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handlePedidoNaoEncontradoException(PedidoNaoEncontradoExceptions ex){
        String erro = ex.getMessage();
        return new ApiErros(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleMethodNotValidExceptions(MethodArgumentNotValidException ex){
       List<String> errors =  ex.getBindingResult().getAllErrors().stream()
                .map(erro -> erro.getDefaultMessage()
                ).collect(Collectors.toList());

        return new ApiErros(errors);
    }

}
