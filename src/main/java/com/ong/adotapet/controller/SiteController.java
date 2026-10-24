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

// Páginas públicas do site "O Sol Nasce para Todos"
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

    // Página de contato com o formulário de adoção.
    // O botão "Quero Adotar" da página /pets chama /contato?animalId=3,
    // e o formulário já abre com esse animal selecionado.
    @GetMapping("/contato")
    public String contato(@RequestParam(required = false) Long animalId, Model model) {
        model.addAttribute("adotante", new Adotante());
        carregarAnimais(model, animalId);
        return "contato";
    }

    // Quando a pessoa envia o formulário, o sistema grava em DUAS tabelas:
    // 1) Adotante, com os dados da pessoa;
    // 2) Adocao, ligando essa pessoa ao animal escolhido.
    @PostMapping("/contato")
    public String enviarPedido(@Valid Adotante adotante, BindingResult result,
                               @RequestParam(required = false) Long animalId,
                               @RequestParam(required = false) String mensagem,
                               Model model, RedirectAttributes redirectAttributes) {

        // O animal não faz parte do Adotante, então conferimos ele à parte
        Animal animal = null;
        if (animalId != null) {
            animal = animalRepository.findById(animalId).orElse(null);
        }

        if (result.hasErrors() || animal == null) {
            if (animal == null) {
                model.addAttribute("erroAnimal", "Escolha o animal que deseja adotar");
            }
            // Devolve o que a pessoa já tinha escolhido e escrito, para não perder
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

    // Lista de animais para o <select> e qual deles deve vir selecionado
    private void carregarAnimais(Model model, Long animalId) {
        model.addAttribute("animais", animalRepository.findByStatus(StatusAnimal.DISPONIVEL));
        model.addAttribute("animalId", animalId);
    }
}
