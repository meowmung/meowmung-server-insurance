package com.example.insurance.insurance.dto.use;

import lombok.Builder;

@Builder
public record PredictionDiseaseDto(
        String pet_type,
        int age,
        int gender,
        int breed,
        float weight,
        float food_count,
        int neutered,
        int current_disease
) {
}
