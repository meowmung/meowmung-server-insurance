package com.example.insurance.insurance.service;

import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.pet.entity.Concerned;
import com.example.insurance.pet.entity.Pet;
import com.example.insurance.pet.entity.code.BreedCode;
import com.example.insurance.pet.repository.BreedCodeRepository;
import com.example.insurance.pet.repository.ConcernedRepository;
import com.example.insurance.pet.repository.PetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final PetRepository petRepository;
    private final BreedCodeRepository breedCodeRepository;
    private final ConcernedRepository concernedRepository;

    // 1차 추천
    public String firstRecommend(RecommendRequest recommendRequest) {
        Pet pet = petRepository.findById(recommendRequest.petId()).orElseThrow(() -> new RuntimeException("그런 펫 없어요"));

        List<Concerned> concerneds = new ArrayList<>();
        for (String item : recommendRequest.concernedName()) {
            Concerned concerned = Concerned.builder()
                    .name(item)
                    .build();
            concerneds.add(concerned);
        }
        pet.addConcerned(concerneds);
        concernedRepository.saveAll(concerneds);
        // 보험 결과 조회

        // 조회 결과 저장

        // 조회 결과 반환

        return "HI";
    }
}
