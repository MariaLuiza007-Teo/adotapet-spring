package com.ong.adotapet.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ong.adotapet.model.Adocao;
import com.ong.adotapet.model.Adotante;
import com.ong.adotapet.model.Animal;
import com.ong.adotapet.model.StatusAnimal;
import com.ong.adotapet.repository.AdocaoRepository;
import com.ong.adotapet.repository.AdotanteRepository;
import com.ong.adotapet.repository.AnimalRepository;

import jakarta.validation.Valid;

@Controller
public class SiteController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private AdocaoRepository adocaoRepository;

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

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
    public String contato(@RequestParam(required = false) Long animalId, Model model) {
        model.addAttribute("adotante", new Adotante());
        carregarAnimais(model, animalId);
        return "contato";
    }

    @PostMapping("/contato")
    public String enviarPedido(@Valid Adotante adotante, BindingResult result,
                               @RequestParam(required = false) Long animalId,
                               @RequestParam(required = false) String mensagem,
                               Model model, RedirectAttributes redirectAttributes) {

        Animal animal = null;
        if (animalId != null) {
            animal = animalRepository.findById(animalId).orElse(null);
        }

        if (result.hasErrors() || animal == null) {
            if (animal == null) {
                model.addAttribute("erroAnimal", "Escolha o animal que deseja adotar");
            }
            model.addAttribute("mensagem", mensagem);
            carregarAnimais(model, animalId);
            return "contato";
        }

        adotanteRepository.save(adotante);

        Adocao adocao = new Adocao();
        adocao.setData(LocalDate.now());
        adocao.setAnimal(animal);
        adocao.setAdotante(adotante);
        adocao.setObservacoes(mensagem);
        adocaoRepository.save(adocao);

        redirectAttributes.addFlashAttribute("sucesso",
                "Pedido enviado! Em breve entraremos em contato sobre a adoção de " + animal.getNome() + ".");
        return "redirect:/contato";
    }

    private void carregarAnimais(Model model, Long animalId) {
        model.addAttribute("animais", animalRepository.findByStatus(StatusAnimal.DISPONIVEL));
        model.addAttribute("animalId", animalId);
    }
}
