package com.example.insurance.pet.controller;

import com.example.insurance.pet.dto.request.PetRequest;
import com.example.insurance.pet.dto.response.PetResponse;
import com.example.insurance.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetResponse> enrollPet(@RequestBody PetRequest petRequest) {
        PetResponse petResponse = petService.enrollPet(petRequest);
        return ResponseEntity.ok(petResponse);
    }
}
