package com.example.insurance.pet.dto.response;

import com.example.insurance.pet.entity.Pet;
import com.example.insurance.pet.entity.code.BreedCode;

public record PetResponse(
        Long petId,
        Integer age,
        Boolean gender,
        Boolean neutered,
        BreedCode breedCode
) {
    public static PetResponse fromEntity(Pet pet) {
        return new PetResponse(
                pet.getPetId(), pet.getAge(), pet.getGender(), pet.getNeutered(), pet.getBreedCode()
        );
    }
}
