package com.oficinaapp.oficina_app.infrastructure.persistence.entity;

import com.oficinaapp.oficina_app.infrastructure.persistence.entity.ClienteEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 10, unique = true)
    private String placa;


    @Column(nullable = false, length = 50)
    private String marca;


    @Column(nullable = false, length = 50)
    private String modelo;


    private Integer ano;


    private String cor;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private ClienteEntity cliente;

}