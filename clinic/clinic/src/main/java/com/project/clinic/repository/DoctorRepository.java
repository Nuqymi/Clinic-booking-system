package com.project.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.clinic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}

