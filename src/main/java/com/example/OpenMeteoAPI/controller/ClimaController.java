package com.example.OpenMeteoAPI.controller;

import com.example.OpenMeteoAPI.model.Cidade;
import com.example.OpenMeteoAPI.model.Clima;
import com.example.OpenMeteoAPI.service.OpenMeteoService;
import com.example.OpenMeteoAPI.Repository.ClimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ClimaController {

    @Autowired
    private OpenMeteoService openMeteoService;

    @Autowired
    private ClimaRepository climaRepository;

    @GetMapping("/cidades/{nomeCidade}/clima")
    public ResponseEntity<?> obterClima(@PathVariable String nomeCidade) {
        try {
            Cidade cidade = openMeteoService.fetchAndSaveWeather(nomeCidade);

            Clima clima = new Clima(
                    cidade,
                    cidade.getTemperaturaAtual(),   // Temperatura atual
                    cidade.getVelocidadeVento(),    // Velocidade do vento
                    LocalDateTime.now()             // Data e hora da consulta
            );

            climaRepository.save(clima);

            return ResponseEntity.ok(clima);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar clima: " + e.getMessage());
        }
    }


    @GetMapping("/consulta-clima/{nomeCidade}")
    public String consultaClima(@PathVariable String nomeCidade, Model model) {
        try {
            Cidade cidade = openMeteoService.fetchAndSaveWeather(nomeCidade);

            Clima clima = new Clima(
                    cidade,
                    cidade.getTemperaturaAtual(),   // Temperatura atual
                    cidade.getVelocidadeVento(),    // Velocidade do vento
                    LocalDateTime.now()             // Data e hora da consulta
            );

            climaRepository.save(clima);

            model.addAttribute("cidade", cidade);
            model.addAttribute("clima", clima);

            return "consultaClima";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao buscar clima: " + e.getMessage());
            return "erro";
        }
    }
}