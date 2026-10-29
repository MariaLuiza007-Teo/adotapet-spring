package com.ong.adotapet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ong.adotapet.model.Animal;
import com.ong.adotapet.model.StatusAnimal;
import com.ong.adotapet.repository.AdocaoRepository;
import com.ong.adotapet.repository.AnimalRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AdocaoRepository adocaoRepository;

    @ModelAttribute("statusList")
    public StatusAnimal[] statusList() {
        return StatusAnimal.values();
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("animais", animalRepository.findAll());
        return "animais/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("animal", new Animal());
        return "animais/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Animal animal, BindingResult result) {
        if (result.hasErrors()) {
            return "animais/formulario";
        }
        animalRepository.save(animal);
        return "redirect:/animais";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("animal", animal);
        return "animais/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        if (adocaoRepository.existsByAnimalId(id)) {
            redirectAttributes.addFlashAttribute("erro",
                    "Não é possível excluir " + animal.getNome() + ": este animal já foi adotado.");
            return "redirect:/animais";
        }

        animalRepository.delete(animal);
        return "redirect:/animais";
    }
}
