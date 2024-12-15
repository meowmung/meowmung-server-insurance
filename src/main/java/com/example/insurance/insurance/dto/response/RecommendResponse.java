package com.example.insurance.insurance.dto.response;

import com.example.insurance.insurance.entity.Insurance;
import com.example.insurance.insurance.entity.Terms;
import java.util.List;

// 보험 1차 추천 요청 응답 값
public record RecommendResponse(
        String insuranceId,
        String logo,
        String company,
        String insuranceItem,
        List<TermsDto> terms

) {
    public record TermsDto(
            String termId,
            String name,
            String causes,
            String limits,
            String details
    ) {
        public static TermsDto from(Terms terms) {
            return new TermsDto(terms.getId(), terms.getName(), terms.getCauses(), terms.getLimits(),
                    terms.getDetails());
        }

    }

    public static RecommendResponse fromEntity(Insurance insurance) {
        List<TermsDto> list = insurance.getTerms().stream().map(TermsDto::from).toList();
        return new RecommendResponse(
                insurance.getInsuranceId(),
                insurance.getLogo(),
                insurance.getCompany(),
                insurance.getInsuranceItem(),

                list
        );
    }

}
