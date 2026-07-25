package com.oficinaapp.oficina_app.application.usecase.cliente;

import com.oficinaapp.oficina_app.application.dto.cliente.ClienteRequest;
import com.oficinaapp.oficina_app.application.dto.cliente.ClienteResponse;
import com.oficinaapp.oficina_app.domain.model.Cliente;
import com.oficinaapp.oficina_app.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CadastrarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public CadastrarClienteUseCase(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponse executar(ClienteRequest request) {

        Cliente cliente = new Cliente();

        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setSenha(request.getSenha());
        cliente.setTelefone(request.getTelefone());
        cliente.setCpf(request.getCpf());
        cliente.setDataNascimento(request.getDataNascimento());

        cliente.setCreatedAt(LocalDateTime.now());
        cliente.setUpdatedAt(LocalDateTime.now());
        cliente.setAtivo(true);

        Cliente clienteSalvo = clienteRepository.salvar(cliente);

        return ClienteResponse.builder()
                .id(clienteSalvo.getId())
                .nome(clienteSalvo.getNome())
                .email(clienteSalvo.getEmail())
                .build();
    }
}