package com.example.insurance.pet.repository;

import com.example.insurance.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
