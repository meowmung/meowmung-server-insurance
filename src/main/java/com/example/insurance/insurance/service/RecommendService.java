package com.example.insurance.insurance.service;

import com.example.insurance.insurance.dto.request.AdditionalRequest;
import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.insurance.dto.response.RecommendResponse;
import com.example.insurance.insurance.entity.Insurance;
import com.example.insurance.insurance.repository.ResultsRepository;
import java.util.ArrayList;
import java.util.List;
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
        // 이거 수정 필요 ML 수정 끝나면 실제 예측질병으로 바꾸기
        // 그냥 이거 삭제하면 됨
        // FIXME : 이거 고쳐야함
        predictionDiseaseName = additionalRequest.currentDisease();

        Insurance results = getTopInsurance(predictionDiseaseName, additionalRequest.petType());
        return RecommendResponse.fromEntity(results);
    }

    public Insurance getTopInsurance(String predictionDiseaseName, String petType) {
        System.out.println("예측질병" + predictionDiseaseName);
        Insurance insurance = resultsRepository.getResult(predictionDiseaseName, petType).orElseThrow();
        return insurance;
    }

}
