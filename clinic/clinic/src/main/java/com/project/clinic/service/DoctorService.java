package com.project.clinic.service;

import com.project.clinic.model.Doctor;
import com.project.clinic.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
}
