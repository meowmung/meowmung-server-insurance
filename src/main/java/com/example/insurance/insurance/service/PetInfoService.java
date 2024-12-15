package com.example.insurance.insurance.service;

import com.example.insurance.insurance.dto.request.AdditionalRequest;
import com.example.insurance.insurance.dto.use.FirstRecommendedDto;
import com.example.insurance.insurance.dto.use.PredictionDiseaseDto;
import com.example.insurance.insurance.dto.response.PredictionDiseaseResponse;
import com.example.insurance.insurance.dto.use.RecommendResultsDto;
import com.example.insurance.insurance.dto.use.SecondRecommendedDto;
import com.example.insurance.insurance.dto.use.SendAdditionalPetInfoDto;
import com.example.insurance.insurance.dto.use.SendPetDto;
import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.insurance.dto.response.RecommendResponse;
import com.example.insurance.insurance.dto.response.RecommendResponse.TermsDto;
import com.example.insurance.insurance.repository.DiseaseCodeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetInfoService {
    private final RestTemplate restTemplate;
    private final DiseaseCodeRepository diseaseCodeRepository;

    @Value("${spring.pet.url.first}")
    private String petFirst;
    @Value("${spring.pet.url.second}")
    private String petSecond;
    @Value("${spring.pet.url.ml-server}")
    private String petMlServer;

    // 보험 결과, level, petId, 우려질병 -> Pet 서버
    @Async
    public CompletableFuture<ResponseEntity<String>> sendPetInfo(RecommendRequest recommendRequest,
                                                                 List<RecommendResponse> recommendResponse) {
        try {
            // 보험결과, level
            List<RecommendResultsDto> recommendResultsDtos = new ArrayList<>();
            for (RecommendResponse item : recommendResponse) {
                List<String> termIds = new ArrayList<>();
                for (TermsDto term : item.terms()) {
                    termIds.add(term.termId());
                }
                RecommendResultsDto recommendResultsDto = RecommendResultsDto.builder()
                        .petId(recommendRequest.petId())
                        .level(1)
                        .insuranceId(item.insuranceId())
                        .termId(termIds)
                        .build();
                recommendResultsDtos.add(recommendResultsDto);
            }

            // petId, 우려질병 + 보험결과 level 담아서 전송
            SendPetDto sendPetDto = SendPetDto.builder()
                    .petId(recommendRequest.petId())
                    .concerneds(recommendRequest.concernedNames())
                    .build();

            FirstRecommendedDto firstRecommendedDto = new FirstRecommendedDto(sendPetDto, recommendResultsDtos);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<FirstRecommendedDto> request = new HttpEntity<>(firstRecommendedDto, headers);

            ResponseEntity<String> response = restTemplate.exchange(petFirst, HttpMethod.POST, request, String.class);
            return CompletableFuture.completedFuture(response);

        } catch (Exception e) {
            log.error("펫 서버로 전송 실패: {}", e.getMessage());
            throw new RuntimeException("펫 서버로 전송 실패", e);
        }
    }

    // 2차 보험 결과, level, petId, 실제질병 -> Pet 서버
    @Async
    public CompletableFuture<ResponseEntity<String>> sendAdditionalPetInfo(AdditionalRequest additionalRequest,
                                                        RecommendResponse recommendResponse) {
        try {
            // 보험결과, level
            List<String> termIds = new ArrayList<>();
            for (TermsDto term : recommendResponse.terms()) {
                termIds.add(term.termId());
            }
            RecommendResultsDto recommendResultsDto = RecommendResultsDto.builder()
                    .petId(additionalRequest.petId())
                    .level(2)
                    .insuranceId(recommendResponse.insuranceId())
                    .termId(termIds)
                    .build();

            // petId, 우려질병 + 보험결과 level 담아서 전송
            SendAdditionalPetInfoDto sendAdditionalPetInfoDto = SendAdditionalPetInfoDto.builder()
                    .petId(additionalRequest.petId())
                    .weight(additionalRequest.weight())
                    .foodCount(additionalRequest.foodCount())
                    .currentDisease(additionalRequest.currentDisease())
                    .build();

            SecondRecommendedDto secondRecommendedDto = new SecondRecommendedDto(sendAdditionalPetInfoDto,
                    recommendResultsDto);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 본문과 헤더를 포함한 HttpEntity 생성
            HttpEntity<SecondRecommendedDto> request = new HttpEntity<>(secondRecommendedDto, headers);

            ResponseEntity<String> response = restTemplate.exchange(petSecond, HttpMethod.POST, request, String.class);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            log.error("펫 서버로 전송 실패: {}", e.getMessage());
            throw new RuntimeException("펫 서버로 전송 실패", e);
        }

    }

    // ML 서버 통신
    public String sendCurrentDisease(AdditionalRequest additionalRequest) {
        int diseaseCode = changeToDiseaseCode(additionalRequest.currentDisease());

        PredictionDiseaseDto predictionDiseaseDto = PredictionDiseaseDto.builder()
                .pet_type(additionalRequest.petType())
                .age(additionalRequest.age())
                .gender(additionalRequest.gender() ? 1 : 0)
                .breed(additionalRequest.breedCode())
                .weight(additionalRequest.weight().floatValue())
                .food_count(additionalRequest.foodCount().floatValue())
                .neutered(additionalRequest.neutered() ? 1 : 0)
                .current_disease(diseaseCode)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PredictionDiseaseDto> request = new HttpEntity<>(predictionDiseaseDto, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(petMlServer, HttpMethod.POST, request,
                    String.class);
            String responseBody = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            PredictionDiseaseResponse predictionDiseaseResponse = objectMapper.readValue(responseBody,
                    PredictionDiseaseResponse.class);

            int predictionDiseaseCode = predictionDiseaseResponse.disease();
            return changeToDiseaseName(predictionDiseaseCode);

        } catch (
                Exception e) {
            log.error("실제 질병 전송 실패: {}", e.getMessage());
            throw new RuntimeException("실제 질병 전송 실패", e);
        }
    }

    public int changeToDiseaseCode (String diseaseName) {
        return diseaseCodeRepository.findByName(diseaseName).getCode();
    }

    public String changeToDiseaseName (int disease) {
        return diseaseCodeRepository.findByCode(disease);
    }

}
