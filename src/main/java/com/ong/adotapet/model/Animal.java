package com.ong.adotapet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A espécie é obrigatória")
    private String especie;

    @NotBlank(message = "O sexo é obrigatório")
    private String sexo;

    private String idade;

    private String foto;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status é obrigatório")
    private StatusAnimal status = StatusAnimal.DISPONIVEL;

    public Animal() {
    }

    public Animal(String nome, String especie, String sexo, String idade, String foto) {
        this.nome = nome;
        this.especie = especie;
        this.sexo = sexo;
        this.idade = idade;
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public StatusAnimal getStatus() {
        return status;
    }

    public void setStatus(StatusAnimal status) {
        this.status = status;
    }
}
