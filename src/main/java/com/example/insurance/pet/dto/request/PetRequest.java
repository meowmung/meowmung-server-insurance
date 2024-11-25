package com.example.insurance.pet.dto.request;

import com.example.insurance.pet.entity.Pet;
import com.example.insurance.pet.entity.code.BreedCode;

public record PetRequest(
        int age,
        boolean gender,
        boolean neutered,
        String breedName
) {
    public Pet toEntity(BreedCode breedCode, PetRequest petRequest){
        return Pet.builder()
                .age(petRequest.age)
                .gender(petRequest.gender)
                .neutered(petRequest.neutered)
                .breedCode(breedCode)
                .build();
    }
}
