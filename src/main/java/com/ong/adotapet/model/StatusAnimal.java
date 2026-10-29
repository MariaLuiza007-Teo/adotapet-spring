package com.ong.adotapet.model;

public enum StatusAnimal {

    DISPONIVEL("Disponível"),
    ADOTADO("Adotado");

    private final String descricao;

    StatusAnimal(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
