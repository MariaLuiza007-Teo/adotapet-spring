package com.ong.adotapet.model;

// Situação do animal na ONG.
// Só os animais DISPONIVEL aparecem na página /pets do site.
public enum StatusAnimal {

    DISPONIVEL("Disponível"),
    ADOTADO("Adotado");

    // Texto "bonito" para mostrar na tela (com acento)
    private final String descricao;

    StatusAnimal(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
