package com.ong.adotapet.repository;

import org.springframework.data.repository.CrudRepository;

import com.ong.adotapet.model.Adocao;

public interface AdocaoRepository extends CrudRepository<Adocao, Long> {

    boolean existsByAnimalId(Long animalId);
}
