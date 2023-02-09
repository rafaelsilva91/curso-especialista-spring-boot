package io.github.rafaelsilva91.rest.controller;

import io.github.rafaelsilva91.exceptions.PedidoNaoEncontradoExceptions;
import io.github.rafaelsilva91.exceptions.RegraNegocioExceptions;
import io.github.rafaelsilva91.rest.ApiErros;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleRegraNegocioExceptions(RegraNegocioExceptions ex){
        String erro = ex.getMessage();
        return new ApiErros(erro);
    }

    @ExceptionHandler(PedidoNaoEncontradoExceptions.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handlerPedidoNaoEncontradoExceptions(PedidoNaoEncontradoExceptions ex){
        String erro = ex.getMessage();
        return new ApiErros(erro);
    }

}
