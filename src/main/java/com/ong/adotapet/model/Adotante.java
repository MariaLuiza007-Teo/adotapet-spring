package com.ong.adotapet.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Adotante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "Digite um e-mail válido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    private String cidade;

    // Um adotante pode ter várias adoções.
    // mappedBy = "adotante": quem guarda a chave estrangeira é a classe Adocao.
    // CascadeType.REMOVE: ao excluir o adotante, as adoções dele também são excluídas.
    // Diferente do roteiro, aqui NÃO usamos orphanRemoval = true: o formulário de
    // edição chega com essa lista vazia, e o orphanRemoval apagaria as adoções
    // do adotante toda vez que ele fosse editado.
    @OneToMany(mappedBy = "adotante", cascade = CascadeType.REMOVE)
    private List<Adocao> adocoes = new ArrayList<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Adocao> getAdocoes() {
        return adocoes;
    }

    public void setAdocoes(List<Adocao> adocoes) {
        this.adocoes = adocoes;
    }
}
