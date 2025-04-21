package com.project.clinic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate day;

    @Column(nullable = false)
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(nullable = false)
    private double fee;

    public Appointment() {}

    // Updated constructor to accept Doctor object instead of doctorId
    public Appointment(User user, LocalDate day, LocalTime time, Doctor doctor, double fee) {
        this.user = user;
        this.day = day;
        this.time = time;
        this.doctor = doctor;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public double getFee() {
        return fee;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", user=" + user.getId() + ", day=" + day + ", time=" + time + 
               ", doctor=" + (doctor != null ? doctor.getName() : "Unassigned") + ", fee=" + fee + "]";
    }
}
