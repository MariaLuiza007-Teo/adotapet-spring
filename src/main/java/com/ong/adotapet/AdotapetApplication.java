package com.ong.adotapet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ong.adotapet.model.Animal;
import com.ong.adotapet.repository.AnimalRepository;

@SpringBootApplication
public class AdotapetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdotapetApplication.class, args);
	}

	@Bean
	CommandLineRunner dadosIniciais(AnimalRepository animalRepository) {
		return args -> {
			if (animalRepository.count() == 0) {
				animalRepository.save(new Animal("Bisnaguinha", "Cachorro", "Macho", "3 meses", "bisnaguinha.jpeg"));
				animalRepository.save(new Animal("Luna", "Gato", "Fêmea", "1 ano", "luna.jpeg"));
				animalRepository.save(new Animal("Caramelo", "Cachorro", "Macho", "3 anos", "caramelo.jpeg"));
			}
		};
	}

}
