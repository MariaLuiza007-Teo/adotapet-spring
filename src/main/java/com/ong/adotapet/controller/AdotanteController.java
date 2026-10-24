package com.ong.adotapet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ong.adotapet.model.Adotante;
import com.ong.adotapet.repository.AdotanteRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/adotantes")
public class AdotanteController {

    @Autowired
    private AdotanteRepository adotanteRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("adotantes", adotanteRepository.findAll());
        return "adotantes/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("adotante", new Adotante());
        return "adotantes/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Adotante adotante, BindingResult result) {
        if (result.hasErrors()) {
            return "adotantes/formulario";
        }
        adotanteRepository.save(adotante);
        return "redirect:/adotantes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Adotante adotante = adotanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("adotante", adotante);
        return "adotantes/formulario";
    }

    // Por causa do CascadeType.REMOVE em Adotante, as adoções dele são excluídas junto
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        Adotante adotante = adotanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        adotanteRepository.delete(adotante);
        return "redirect:/adotantes";
    }
}
