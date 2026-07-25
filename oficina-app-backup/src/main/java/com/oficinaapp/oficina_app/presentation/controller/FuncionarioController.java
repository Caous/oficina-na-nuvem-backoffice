package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.funcionario.FuncionarioRequest;
import com.oficinaapp.oficina_app.application.dto.funcionario.FuncionarioResponse;
import com.oficinaapp.oficina_app.application.usecase.funcionario.CadastrarFuncionarioUseCase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee/")
public class FuncionarioController {

    private final CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase;

    public FuncionarioController(CadastrarFuncionarioUseCase cadastrarFuncionarioUseCase){
        this.cadastrarFuncionarioUseCase = cadastrarFuncionarioUseCase;
    }

    public FuncionarioResponse cadastrar(@RequestBody FuncionarioRequest request){
        return cadastrarFuncionarioUseCase.executar(request);
    }

}
