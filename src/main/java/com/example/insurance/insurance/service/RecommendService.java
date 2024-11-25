package com.example.insurance.insurance.service;

import com.example.insurance.insurance.dto.AdditionalDto;
import com.example.insurance.insurance.dto.request.AdditionalRequest;
import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.pet.entity.AdditionalInfo;
import com.example.insurance.pet.entity.Concerned;
import com.example.insurance.pet.entity.Pet;
import com.example.insurance.pet.entity.code.DiseaseCode;
import com.example.insurance.pet.repository.ConcernedRepository;
import com.example.insurance.pet.repository.DiseaseCodeRepository;
import com.example.insurance.pet.repository.PetRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final PetRepository petRepository;
    private final ConcernedRepository concernedRepository;
    private final DiseaseCodeRepository diseaseCodeRepository;

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

    public String additionalRecommend(AdditionalRequest additionalRequest) {
        Pet pet = petRepository.findById(additionalRequest.petId()).orElseThrow(() -> new RuntimeException("그런 펫 없는데"));
        DiseaseCode diseaseCode = diseaseCodeRepository.findById(additionalRequest.diseaseName()).orElseThrow(() -> new RuntimeException("그런 질병 없는데요"));
        AdditionalInfo additionalInfo = additionalRequest.toEntity(additionalRequest, diseaseCode);
        pet.addAdditionalInfo(additionalInfo);
        petRepository.save(pet);

        AdditionalDto additionalDto = new AdditionalDto(pet.getAge(),
                                                        pet.getGender(),
                                                        pet.getNeutered(),
                                                        pet.getBreedCode().getCode(),
                                                        pet.getAdditionalInfo().getWeight(),
                                                        pet.getAdditionalInfo().getFoodCount()
                                                        );

        RestTemplate restTemplate = new RestTemplate();
        // 요청 매개변수 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AdditionalDto> request = new HttpEntity<>(additionalDto, headers);

//        ResponseDto responseDto = restTemplate.exchange(url, HttpMethod.POST, request, ResponseDto.class).getBody();

        return "hi";
    }




}
