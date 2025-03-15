package com.example.OpenMeteoAPI.controller;

import com.example.OpenMeteoAPI.model.Cidade;
import com.example.OpenMeteoAPI.service.OpenMeteoService;
import com.example.OpenMeteoAPI.Repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private OpenMeteoService openMeteoService;

    // Endpoint para listar todas as cidades (acessível para Admin e User)
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<Cidade>> listarCidades() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return ResponseEntity.ok(cidades);
    }

    // Endpoint para obter o clima de uma cidade específica (acessível para Admin e User)
    @GetMapping("/{id}/clima")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> obterClima(@PathVariable Long id) {
        return cidadeRepository.findById(id)
                .map(cidade -> {
                    try {
                        cidade = openMeteoService.fetchAndSaveWeather(cidade.getNome()); // Obtém dados climáticos
                        return ResponseEntity.ok(cidade);  // Retorna cidade com clima atualizado
                    } catch (Exception e) {
                        return ResponseEntity.status(500).body("Erro ao buscar dados climáticos: " + e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para cadastrar uma nova cidade (acessível somente para Admin)
    @PostMapping("/cadastro-cidade")
    @PreAuthorize("hasRole('ADMIN')")
    public String cadastrarCidade(@ModelAttribute Cidade cidade, Model model) {
        try {
            Cidade novaCidade = cidadeRepository.save(cidade); // Salva a nova cidade no banco de dados
            model.addAttribute("mensagem", "Cidade cadastrada com sucesso!");
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar cidade: " + e.getMessage());
        }
        return "cadastro-cidade";  // Retorna a página de cadastro
    }


}