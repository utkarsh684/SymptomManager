package com.example.symptom.controller;

import com.example.symptom.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    @Autowired
    private OpenAiService openAiService;

    public ResponseEntity<String> analyze(@RequestBody Map<String, String> payload){
        try{
            String symptom=payload.get("symptom");
            String department = openAiService.analyzeSymptom(symptom);
            return ResponseEntity.ok(department);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
