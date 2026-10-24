package com.ong.adotapet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ong.adotapet.model.StatusAnimal;
import com.ong.adotapet.repository.AnimalRepository;

// Páginas públicas do site "O Sol Nasce para Todos"
@Controller
public class SiteController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    // Antes os animais estavam escritos direto no HTML.
    // Agora eles vêm do banco: só os que ainda estão disponíveis.
    @GetMapping("/pets")
    public String pets(Model model) {
        model.addAttribute("animais", animalRepository.findByStatus(StatusAnimal.DISPONIVEL));
        return "pets";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/contato")
    public String contato() {
        return "contato";
    }
}
