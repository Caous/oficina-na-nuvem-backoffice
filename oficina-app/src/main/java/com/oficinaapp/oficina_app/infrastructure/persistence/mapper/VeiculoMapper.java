package com.oficinaapp.oficina_app.infrastructure.persistence.mapper;

import com.oficinaapp.oficina_app.domain.model.Veiculo;
import com.oficinaapp.oficina_app.infrastructure.persistence.entity.VeiculoEntity;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    public VeiculoEntity toEntity(Veiculo veiculo){

        return VeiculoEntity.builder()
                .id(veiculo.getId())
                .placa(veiculo.getPlaca())
                .marca(veiculo.getMarca())
                .modelo(veiculo.getModelo())
                .ano(veiculo.getAno())
                .cor(veiculo.getCor())
                .build();

    }

    public Veiculo toDomain(VeiculoEntity entity){

        return Veiculo.builder()
                .id(entity.getId())
                .placa(entity.getPlaca())
                .marca(entity.getMarca())
                .modelo(entity.getModelo())
                .ano(entity.getAno())
                .cor(entity.getCor())
                .build();

    }
}
