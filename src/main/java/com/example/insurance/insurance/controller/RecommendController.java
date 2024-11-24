package com.example.insurance.insurance.controller;

import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.insurance.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/recommended")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    @PostMapping
    public String firstRecommend(@RequestParam RecommendRequest recommendRequest) {
        return recommendService.firstRecommend(recommendRequest);
    }

}
