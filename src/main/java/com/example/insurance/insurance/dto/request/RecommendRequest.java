package com.example.insurance.insurance.dto.request;

import java.util.List;

// 1차
public record RecommendRequest(
        Long petId,
        String petType,
        List<String> concernedNames
) {

}
