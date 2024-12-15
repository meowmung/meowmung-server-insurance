package com.example.insurance.insurance.service;

import com.example.insurance.insurance.dto.request.AdditionalRequest;
import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.insurance.dto.response.RecommendResponse;
import com.example.insurance.insurance.entity.Insurance;
import com.example.insurance.insurance.entity.Terms;
import com.example.insurance.insurance.repository.InsuranceRepository;
import com.example.insurance.insurance.repository.ResultsRepository;
import com.example.insurance.insurance.repository.TermsRepsitory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final ResultsRepository resultsRepository;
    private final TermsRepsitory termsRepsitory;
    private final InsuranceRepository insuranceRepository;

    public List<RecommendResponse> firstRecommend(RecommendRequest recommendRequest) {
        List<RecommendResponse> results = getTopInsurances(recommendRequest.concernedNames(),
                recommendRequest.petType());

        results.forEach(System.out::println);

        return results;

    }

    public List<RecommendResponse> getTopInsurances(List<String> concernedNames, String petType) {
        List<Insurance> insurances = resultsRepository.getResults(concernedNames, petType);
        List<RecommendResponse> recommendResponses = new ArrayList<>();

        for (Insurance insurance : insurances) {
            Insurance insurance1 = insuranceRepository.findById(insurance.getInsuranceId())
                    .orElseThrow(() -> new RuntimeException("해당 보험이 존재하지 않습니다."));

            List<Terms> terms = termsRepsitory.getTerms(insurance1.getInsuranceId(), concernedNames);
            Insurance insurance2 = Insurance.builder()
                    .insuranceId(insurance1.getInsuranceId())
                    .insuranceItem(insurance1.getInsuranceItem())
                    .company(insurance1.getCompany())
                    .logo(insurance1.getLogo())
                    .terms(terms)
                    .build();

            recommendResponses.add(RecommendResponse.fromEntity(insurance2));

        }

        return recommendResponses;
    }

    // 2차 추천
    public RecommendResponse additionalRecommend(AdditionalRequest additionalRequest, String predictionDiseaseName) {
        Insurance results = getTopInsurance(predictionDiseaseName, additionalRequest.petType());

        return RecommendResponse.fromEntity(results);
    }

    public Insurance getTopInsurance(String predictionDiseaseName, String petType) {
        if (Objects.equals(predictionDiseaseName, "정상")) {
            predictionDiseaseName = "기타";
        }
        Insurance insurance = resultsRepository.getResult(predictionDiseaseName, petType).orElseThrow();

        List<RecommendResponse> recommendResponses = new ArrayList<>();

        Insurance insurance1 = insuranceRepository.findById(insurance.getInsuranceId())
                .orElseThrow(() -> new RuntimeException("해당 보험이 존재하지 않습니다."));

        List<Terms> terms = termsRepsitory.getSecondTerms(insurance1.getInsuranceId(), predictionDiseaseName);

        Insurance insurance2 = Insurance.builder()
                .insuranceId(insurance1.getInsuranceId())
                .insuranceItem(insurance1.getInsuranceItem())
                .company(insurance1.getCompany())
                .logo(insurance1.getLogo())
                .terms(terms)
                .build();

        recommendResponses.add(RecommendResponse.fromEntity(insurance2));

        return insurance2;
    }

}
