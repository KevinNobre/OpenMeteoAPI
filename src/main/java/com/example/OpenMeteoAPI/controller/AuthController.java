package com.example.OpenMeteoAPI.controller;

import com.example.OpenMeteoAPI.Repository.CidadeRepository;
import com.example.OpenMeteoAPI.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logout", "You have been logged out.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        // Simulação de registro do usuário
        model.addAttribute("message", "User registered successfully!");
        return "redirect:/login";
    }
    @GetMapping("/home")
    public String home() {
        return "home";  // Retorna a página inicial do aplicativo
    }

    @GetMapping("/cadastro-cidade")
    public String cadastroCidade(){

        return "cadastro-cidade";
    }
}
