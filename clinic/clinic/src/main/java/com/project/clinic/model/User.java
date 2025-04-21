package com.project.clinic.model;

import java.util.ArrayList;
import java.util.List; // Missing import

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; 
    
    private String password;
    private String fullname;
    private String role;  

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public User() {}

    public User(String email, String password, String fullname, String role) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.appointments = new ArrayList<>(); // Initialize appointments list
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {  
        return email;
    }

    public String getPassword() { 
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", fullname=" + fullname + ", role=" + role + "]";
    }
}
