package com.example.insurance.insurance.dto.request;

import com.example.insurance.pet.entity.AdditionalInfo;
import com.example.insurance.pet.entity.code.DiseaseCode;

public record AdditionalRequest (
        Integer weight,
        Integer foodCount,
        String diseaseName,

        Long petId
) {
    public AdditionalInfo toEntity(AdditionalRequest additionalRequest, DiseaseCode diseaseCode) {
        return AdditionalInfo.builder()
                .weight(additionalRequest.weight())
                .foodCount(additionalRequest.foodCount())
                .diseaseCode(diseaseCode)
                .build();
    }
}
