package com.example.insurance.insurance.dto.use;

import java.util.List;
import lombok.Builder;

@Builder
public record SendPetDto(
        Long petId,
        List<String> concerneds
) {
}
