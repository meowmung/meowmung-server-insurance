package com.example.insurance.insurance.controller;

import com.example.insurance.insurance.dto.request.AdditionalRequest;
import com.example.insurance.insurance.dto.request.RecommendRequest;
import com.example.insurance.insurance.dto.response.RecommendResponse;
import com.example.insurance.insurance.service.PetInfoService;
import com.example.insurance.insurance.service.RecommendService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/recommended")
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final RecommendService recommendService;
    private final PetInfoService petInfoService;

    // 1차 추천
    @PostMapping
//    public List<RecommendResponse> firstRecommend(@RequestBody RecommendRequest recommendRequest) {
    public void firstRecommend(@RequestBody RecommendRequest recommendRequest) {
        // 보험 결과 추출 해서 client 한테 보내기
        List<RecommendResponse> recommendResponses = recommendService.firstRecommend(recommendRequest);

        // 보험 결과 pet 서버로 보내기
        CompletableFuture<ResponseEntity<String>> future = petInfoService.sendPetInfo(recommendRequest, recommendResponses);
        future.thenAccept(response -> {
            log.info("펫 서버로 데이터 전송 성공: {}", response.getBody());
        }).exceptionally(ex -> {
            log.error("펫 서버로 데이터 전송 중 오류 발생: {}", ex.getMessage());
            return null;
        });
    }

    @PostMapping("/additional")
    public RecommendResponse additionalRecommend(@RequestBody AdditionalRequest additionalRequest) {

        String predictionDiseaseName = petInfoService.sendCurrentDisease(additionalRequest);
        // 보험 결과 추출해서 client 한테 보내기
        RecommendResponse recommendResponse = recommendService.additionalRecommend(additionalRequest,
                predictionDiseaseName);

        // 보험 결과 펫 서버로 보내기
        CompletableFuture<ResponseEntity<String>> future = petInfoService.sendAdditionalPetInfo(additionalRequest, recommendResponse);
        future.thenAccept(response -> {
            log.info("펫 서버로 데이터 전송 성공: {}", response.getBody());
        }).exceptionally(ex -> {
            log.error("펫 서버로 데이터 전송 중 오류 발생: {}", ex.getMessage());
            return null;
        });

        return recommendResponse;
    }

}
