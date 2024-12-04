package com.example.insurance.insurance.dto.request;

// 받는 추가 정보
public record AdditionalRequest (
        Long petId,
        Integer age,
        Boolean gender,
        Boolean neutered,
        String breedName,
        String petType,
        int breedCode,

        Integer weight,
        Integer foodCount,
        String currentDisease
//        String predictionDiseaseName
) {
//    public AdditionalInfo toEntity(AdditionalRequest additionalRequest, DiseaseCode diseaseCode) {
//        return AdditionalInfo.builder()
//                .weight(additionalRequest.weight())
//                .foodCount(additionalRequest.foodCount())
//                .diseaseCode(diseaseCode)
//                .build();
//    }
}
