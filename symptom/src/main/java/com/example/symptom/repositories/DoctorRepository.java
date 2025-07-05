package com.example.symptom.repositories;

import com.example.symptom.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
}
