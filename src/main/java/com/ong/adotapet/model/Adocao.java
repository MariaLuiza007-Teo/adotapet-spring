package com.ong.adotapet.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

// Tabela que liga um Animal a um Adotante.
// No roteiro, Carro se liga só a Marca; aqui Adocao se liga a DUAS tabelas.
@Entity
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @DateTimeFormat ensina o Spring a ler a data no formato do <input type="date"> (aaaa-mm-dd)
    @NotNull(message = "A data é obrigatória")
    @PastOrPresent(message = "A data não pode estar no futuro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    // O banco guarda até 255 caracteres por padrão, então limitamos aqui também
    @Size(max = 255, message = "As observações podem ter no máximo 255 caracteres")
    private String observacoes;

    // Muitas adoções podem apontar para o mesmo animal
    @ManyToOne
    @JoinColumn(name = "animal_id")
    @NotNull(message = "Selecione um animal")
    private Animal animal;

    // Muitas adoções podem apontar para o mesmo adotante
    @ManyToOne
    @JoinColumn(name = "adotante_id")
    @NotNull(message = "Selecione um adotante")
    private Adotante adotante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Adotante getAdotante() {
        return adotante;
    }

    public void setAdotante(Adotante adotante) {
        this.adotante = adotante;
    }
}
