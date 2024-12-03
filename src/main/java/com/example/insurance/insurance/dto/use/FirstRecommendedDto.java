package com.example.insurance.insurance.dto.use;

import java.util.List;

public record FirstRecommendedDto(
        SendPetDto sendPetDto,
        List<RecommendResultsDto> recommendResultsDto
) {
}
