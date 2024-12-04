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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PetInfoService {
    private final RestTemplate restTemplate;
    private final DiseaseCodeRepository diseaseCodeRepository;

    // 보험 결과, level, petId, 우려질병 -> Pet 서버
    public ResponseEntity<String> sendPetInfo(RecommendRequest recommendRequest,
                                                List<RecommendResponse> recommendResponse) {
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

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문과 헤더를 포함한 HttpEntity 생성
        HttpEntity<FirstRecommendedDto> request = new HttpEntity<>(firstRecommendedDto, headers);

        // Pet 서버 URL
        String url = "http://localhost:8080/pet/recommend";

        // 요청 전송 및 응답 처리
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return response;

    }

    // 2차 보험 결과, level, petId, 실제질병 -> Pet 서버
    public ResponseEntity<String> sendAdditionalPetInfo(AdditionalRequest additionalRequest,
                                                          RecommendResponse recommendResponse) {
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

        SecondRecommendedDto secondRecommendedDto = new SecondRecommendedDto(sendAdditionalPetInfoDto, recommendResultsDto);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문과 헤더를 포함한 HttpEntity 생성
        HttpEntity<SecondRecommendedDto> request = new HttpEntity<>(secondRecommendedDto, headers);

        // Pet 서버 URL
        String url = "http://localhost:8080/pet/additional";

        // 요청 전송 및 응답 처리
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return response;
        } catch (
                Exception e) {
            // 예외 처리 (로그 출력 등)
            System.err.println("펫서버로 펫정보 전송 실패: " + e.getMessage());
            throw new RuntimeException("펫서버로 펫 정보 전송 실패", e);
        }


    }

    public String sendCurrentDisease(AdditionalRequest additionalRequest) {
        int diseaseCode = changeToDiseaseCode(additionalRequest.currentDisease());

        // 타입
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

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문과 헤더를 포함한 HttpEntity 생성
        HttpEntity<PredictionDiseaseDto> request = new HttpEntity<>(predictionDiseaseDto, headers);

        // ML 서버 url
        String url = "http://192.168.1.2:8000/insurance/recommend";

        // 요청 전송 및 응답 처리
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            String responseBody = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            PredictionDiseaseResponse predictionDiseaseResponse = objectMapper.readValue(responseBody, PredictionDiseaseResponse.class);

            int predictionDiseaseCode = predictionDiseaseResponse.predictionDiseaseCode();

            return changeToDiseaseName(predictionDiseaseCode);
        } catch (
                Exception e) {
            // 예외 처리 (로그 출력 등)
            System.err.println("실제 질병 전송 실패: " + e.getMessage());
            throw new RuntimeException("실제 질병 전송 실패", e);
        }
    }

    public int changeToDiseaseCode (String diseaseName) {
        System.out.println(diseaseCodeRepository.findByName(diseaseName).getCode());
        return diseaseCodeRepository.findByName(diseaseName).getCode();
    }

    public String changeToDiseaseName (int diseaseCode) {
        System.out.println(diseaseCodeRepository.findByCode(diseaseCode).getName());
        return diseaseCodeRepository.findByCode(diseaseCode).getName();
    }

}
