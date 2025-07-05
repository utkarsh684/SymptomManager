package com.example.symptom.controller;

import com.example.symptom.entities.Appointment;
import com.example.symptom.entities.Doctor;
import com.example.symptom.entities.User;
import com.example.symptom.repositories.AppointmentRepository;
import com.example.symptom.repositories.DoctorRepository;
import com.example.symptom.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@CrossOrigin
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Map<String, String> payload) {
        try {
            Long userId = Long.parseLong(payload.get("userId"));
            Long doctorId = Long.parseLong(payload.get("doctorId"));
            LocalDateTime time = LocalDateTime.parse(payload.get("appointmentTime"));

            User user = userRepository.findById(userId).orElseThrow();
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

            Appointment appointment = new Appointment();
            appointment.setUser(user);
            appointment.setDoctor(doctor);
            appointment.setAppointmentTime(time);
            appointment.setStatus("Booked");

            return ResponseEntity.ok(appointmentRepository.save(appointment));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error booking appointment: " + e.getMessage());
        }
    }

    @GetMapping("/by-user")
    public ResponseEntity<?> getAppointmentsByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(appointmentRepository.findByUserId(userId));
    }

}
