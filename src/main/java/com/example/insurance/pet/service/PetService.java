package com.example.insurance.pet.service;

import com.example.insurance.pet.dto.request.PetRequest;
import com.example.insurance.pet.dto.response.PetResponse;
import com.example.insurance.pet.entity.Pet;
import com.example.insurance.pet.entity.code.BreedCode;
import com.example.insurance.pet.repository.BreedCodeRepository;
import com.example.insurance.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final BreedCodeRepository breedCodeRepository;

    // 펫 정보
    public PetResponse enrollPet(PetRequest petRequest) {
        BreedCode breedCode = getBreedCode(petRequest);
        Pet savedPet = petRepository.save(petRequest.toEntity(breedCode, petRequest));
        PetResponse petResponse = PetResponse.fromEntity(savedPet);
        return petResponse;
    }

    // 종 코드 찾기
    private BreedCode getBreedCode(PetRequest petRequest) {
        return breedCodeRepository.findById(petRequest.breedName())
                .orElseThrow(() -> new RuntimeException("그런 종 없는데요"));
    }

}
