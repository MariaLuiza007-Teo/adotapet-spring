package com.ong.adotapet.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ong.adotapet.model.Adocao;
import com.ong.adotapet.model.Animal;
import com.ong.adotapet.model.StatusAnimal;
import com.ong.adotapet.repository.AdocaoRepository;
import com.ong.adotapet.repository.AdotanteRepository;
import com.ong.adotapet.repository.AnimalRepository;

import jakarta.validation.Valid;

// Segue o modelo do CarroController do roteiro,
// mas o formulário precisa de DUAS listas: animais e adotantes.
@Controller
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AdotanteRepository adotanteRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("adocoes", adocaoRepository.findAll());
        return "adocoes/listar";
    }

    // O botão "Quero Adotar" da página /pets chama /adocoes/novo?animalId=3.
    // required = false: se o animalId não vier, o formulário abre sem animal selecionado.
    @GetMapping("/novo")
    public String novo(@RequestParam(required = false) Long animalId, Model model) {
        Adocao adocao = new Adocao();
        adocao.setData(LocalDate.now());

        if (animalId != null) {
            animalRepository.findById(animalId).ifPresent(adocao::setAnimal);
        }

        model.addAttribute("adocao", adocao);
        carregarListas(model, adocao);
        return "adocoes/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Adocao adocao, BindingResult result, Model model) {
        // O <select> envia só o id (animal.id). Se a pessoa deixar "Selecione...",
        // o Spring cria um Animal vazio, sem id, e o @NotNull não percebe.
        // Por isso conferimos o id aqui na mão.
        if (adocao.getAnimal() != null && adocao.getAnimal().getId() == null) {
            result.rejectValue("animal", "NotNull", "Selecione um animal");
        }
        if (adocao.getAdotante() != null && adocao.getAdotante().getId() == null) {
            result.rejectValue("adotante", "NotNull", "Selecione um adotante");
        }

        if (result.hasErrors()) {
            // Ao voltar para o formulário, as listas dos <select> precisam ser carregadas de novo
            carregarListas(model, adocao);
            return "adocoes/formulario";
        }
        adocaoRepository.save(adocao);
        return "redirect:/adocoes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Adocao adocao = adocaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("adocao", adocao);
        carregarListas(model, adocao);
        return "adocoes/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        Adocao adocao = adocaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        adocaoRepository.delete(adocao);
        return "redirect:/adocoes";
    }

    // Coloca no model as listas usadas nos dois <select> do formulário
    private void carregarListas(Model model, Adocao adocao) {
        List<Animal> animais = new ArrayList<>(animalRepository.findByStatus(StatusAnimal.DISPONIVEL));

        // Na edição, o animal desta adoção provavelmente já está como ADOTADO.
        // Ele precisa continuar na lista, senão o <select> perde a seleção.
        if (adocao.getAnimal() != null && adocao.getAnimal().getId() != null) {
            Animal atual = animalRepository.findById(adocao.getAnimal().getId()).orElse(null);
            if (atual != null && atual.getStatus() != StatusAnimal.DISPONIVEL) {
                animais.add(atual);
            }
        }

        model.addAttribute("animais", animais);
        model.addAttribute("adotantes", adotanteRepository.findAll());
    }
}
