package com.example.insurance.insurance.dto.use;

import lombok.Builder;

@Builder
public record SendAdditionalPetInfoDto(
        Long petId,
        Integer weight,
        Integer foodCount,
        String currentDisease

) {
}
