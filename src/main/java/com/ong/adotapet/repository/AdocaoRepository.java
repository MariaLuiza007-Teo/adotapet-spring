package com.ong.adotapet.repository;

import org.springframework.data.repository.CrudRepository;

import com.ong.adotapet.model.Adocao;

public interface AdocaoRepository extends CrudRepository<Adocao, Long> {

    // Responde "true" se existe alguma adoção para o animal com esse id.
    // Usado para bloquear a exclusão de um animal que já foi adotado.
    boolean existsByAnimalId(Long animalId);
}
