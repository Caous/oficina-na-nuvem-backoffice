package com.oficinaapp.oficina_app.presentation.controller;

import com.oficinaapp.oficina_app.application.dto.cliente.ClienteRequest;
import com.oficinaapp.oficina_app.application.dto.cliente.ClienteResponse;
import com.oficinaapp.oficina_app.application.usecase.cliente.CadastrarClienteUseCase;
import com.oficinaapp.oficina_app.domain.model.Cliente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/")
public class ClienteController {

    private final CadastrarClienteUseCase cadastrarClienteUseCase;

    public ClienteController(CadastrarClienteUseCase cadastrarClienteUseCase){
        this.cadastrarClienteUseCase = cadastrarClienteUseCase;

    }

    @PostMapping
    public ClienteResponse cadastrar(@RequestBody ClienteRequest request){
        return cadastrarClienteUseCase.executar(request);
    }

}
