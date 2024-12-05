package com.example.insurance.insurance.service;

import com.example.insurance.insurance.dto.request.AdditionalRequest;
import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.insurance.dto.response.RecommendResponse;
import com.example.insurance.insurance.entity.Insurance;
import com.example.insurance.insurance.repository.ResultsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final ResultsRepository resultsRepository;

    public List<RecommendResponse> firstRecommend(RecommendRequest recommendRequest) {
        List<Insurance> results = getTopInsurances(recommendRequest.concernedNames(), recommendRequest.petType());
        List<RecommendResponse> recommendResponses = new ArrayList<>();
        for (Insurance result : results) {
            recommendResponses.add(RecommendResponse.fromEntity(result));
        }
        return recommendResponses;
    }

    public List<Insurance> getTopInsurances(List<String> concernedNames, String petType) {
        List<Insurance> results = resultsRepository.getResults(concernedNames, petType);
        return results;
    }

    // 2차 추천
    public RecommendResponse additionalRecommend(AdditionalRequest additionalRequest, String predictionDiseaseName) {
        Insurance results = getTopInsurance(predictionDiseaseName, additionalRequest.petType());

        return RecommendResponse.fromEntity(results);
    }

    public Insurance getTopInsurance(String predictionDiseaseName, String petType) {
        if (Objects.equals(predictionDiseaseName, "정상")){
            predictionDiseaseName = "기타";
        }
        Insurance insurance = resultsRepository.getResult(predictionDiseaseName, petType).orElseThrow();
        return insurance;
    }

}
