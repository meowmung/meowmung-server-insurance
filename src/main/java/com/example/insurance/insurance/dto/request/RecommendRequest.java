package com.example.insurance.insurance.dto.request;

import java.util.List;

// 1차
public record RecommendRequest (
        Long petId,
        String petType,
        List<String> concernedNames
) {
//    public  toEntity(Breed breed) {
//        return Pet.builder()
//                .petName(petName)
//                .age(age)
//                .gender(gender)
//                .neutered(neutered)
//                .breed(breed)
//                .build();
//
//    }

}
