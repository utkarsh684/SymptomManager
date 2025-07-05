package com.example.symptom.controller;

import com.example.symptom.entities.Doctor;
import com.example.symptom.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    @GetMapping("/by-specilaity")
    public List<Doctor> getBySpecialty(@RequestParam String specilaity){
        return doctorRepository.findBySpecialtyIgnoreCase(specilaity);
    }

    public ResponseEntity<Doctor> add(@RequestBody Doctor doctor){
        return ResponseEntity.ok(doctorRepository.save(doctor));
    }

}
