package com.example.insurance.insurance.dto.use;

import com.example.insurance.insurance.entity.Terms;

public record TermsDto(
        String termId,
        String name,
        String causes,
        String limits,
        String details
) {

}
