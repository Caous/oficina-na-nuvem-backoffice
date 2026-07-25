package com.oficinaapp.oficina_app.domain.model;

import com.oficinaapp.oficina_app.domain.enums.StatusOrdemServico;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrdemServico {


        private Long id;

        private Cliente cliente;

        private Veiculo veiculo;

        private Funcionario funcionario;

        private List<Servico> servicos;

        private List<Peca> pecas;

        private StatusOrdemServico status;

        private LocalDateTime dataAbertura;

        private LocalDateTime dataFinalizacao;

        private BigDecimal valorTotal;
}
