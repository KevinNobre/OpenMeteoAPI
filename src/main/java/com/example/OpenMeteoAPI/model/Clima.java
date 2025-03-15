package com.example.OpenMeteoAPI.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Clima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    private Double temperaturaAtual;
    private Double velocidadeVento;
    private LocalDateTime dataHoraConsulta;

    // Construtor com argumentos
    public Clima(Cidade cidade, Double temperaturaAtual, Double velocidadeVento, LocalDateTime dataHoraConsulta) {
        this.cidade = cidade;
        this.temperaturaAtual = temperaturaAtual;
        this.velocidadeVento = velocidadeVento;
        this.dataHoraConsulta = dataHoraConsulta;
    }

    // Construtor sem argumentos
    public Clima() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(Double temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public Double getVelocidadeVento() {
        return velocidadeVento;
    }

    public void setVelocidadeVento(Double velocidadeVento) {
        this.velocidadeVento = velocidadeVento;
    }

    public LocalDateTime getDataHoraConsulta() {
        return dataHoraConsulta;
    }

    public void setDataHoraConsulta(LocalDateTime dataHoraConsulta) {
        this.dataHoraConsulta = dataHoraConsulta;
    }
}