package com.example.insurance.insurance.dto.request;

import java.util.List;

public record RecommendRequest (
        Long petId,
        List<String> concernedName
) {
}
