package com.example.insurance.insurance.dto.use;

import com.example.insurance.insurance.entity.Terms;

public record TermsDto(
        String termId,
        String name,
        String causes,
        String limits,
        String details
) {

    public static TermsDto fromEntity(Terms terms) {
        return new TermsDto(
                terms.getId(),
                terms.getName(),
                terms.getCauses(),
                terms.getLimits(),
                terms.getDetails()
        );
    }
}
