package com.ong.adotapet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ong.adotapet.model.Animal;
import com.ong.adotapet.model.StatusAnimal;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    List<Animal> findByStatus(StatusAnimal status);
}
