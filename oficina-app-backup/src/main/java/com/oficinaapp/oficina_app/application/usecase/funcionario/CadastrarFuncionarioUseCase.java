package com.oficinaapp.oficina_app.application.usecase.funcionario;

import com.oficinaapp.oficina_app.application.dto.funcionario.FuncionarioRequest;
import com.oficinaapp.oficina_app.application.dto.funcionario.FuncionarioResponse;
import com.oficinaapp.oficina_app.domain.model.Funcionario;
import com.oficinaapp.oficina_app.domain.repository.FuncionariosRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CadastrarFuncionarioUseCase {

    private final FuncionariosRepository funcionariosRepository;

    public CadastrarFuncionarioUseCase(FuncionariosRepository funcionariosRepository){
        this.funcionariosRepository = funcionariosRepository;
    }

    public FuncionarioResponse executar(FuncionarioRequest request){

        Funcionario funcionario = new Funcionario();

        funcionario.setNome(request.getNome());
        funcionario.setEmail(request.getEmail());
        funcionario.setSenha(request.getSenha());
        funcionario.setTelefone(request.getTelefone());
        funcionario.setCpf(request.getCpf());
        funcionario.setDataNascimento(request.getDataNascimento());

        funcionario.setCreatedAt(LocalDateTime.now());
        funcionario.setUpdatedAt(LocalDateTime.now());
        funcionario.setAtivo(true);

        Funcionario funcionarioSalvo = funcionariosRepository.salvar(funcionario);

        return FuncionarioResponse.builder()
                .id(funcionarioSalvo.getId())
                .nome(funcionarioSalvo.getNome())
                .email(funcionarioSalvo.getEmail())
                .build();

    }

}
