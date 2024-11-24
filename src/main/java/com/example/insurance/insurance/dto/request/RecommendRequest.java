package com.example.insurance.insurance.dto.request;

import com.example.insurance.pet.entity.Concerned;
import com.example.insurance.pet.entity.Pet;
import com.example.insurance.pet.entity.code.BreedCode;
import java.util.List;

public record RecommendRequest (
        int age,
        boolean gender,
        boolean neutered,
        String breedName,
        List<String> concernedName
) {
    public Pet toEntity(List<Concerned> concerneds, BreedCode breedCode, RecommendRequest recommendRequest) {
        return Pet.builder()
                .age(recommendRequest.age)
                .gender(recommendRequest.gender)
                .neutered(recommendRequest.neutered)
                .breedCode(breedCode)
                .concerned(concerneds)
                .build();
    }
}
