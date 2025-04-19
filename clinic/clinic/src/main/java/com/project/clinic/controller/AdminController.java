package com.project.clinic.controller;

import com.project.clinic.model.Appointment;
import com.project.clinic.model.Doctor;
import com.project.clinic.model.User;
import com.project.clinic.repository.AppointmentRepository;
import com.project.clinic.repository.DoctorRepository;
import com.project.clinic.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // View Admin Dashboard with Appointments and Doctor List
    @GetMapping("/admin_dashboard")
    public String adminDashboard(Model model) {
        System.out.println("Admin Dashboard Loaded!");

        List<Appointment> appointments = appointmentRepository.findAll();
        List<Doctor> doctors = doctorRepository.findAll();

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctors", doctors);

        return "admin_dashboard";
    }

    // Assign a doctor to an appointment
    @PostMapping("/assign-doctor")
    public String assignDoctorToAppointment(@RequestParam Long appointmentId, @RequestParam Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if (appointment != null && doctor != null) {
            appointment.setDoctor(doctor);
            appointmentRepository.save(appointment);
            System.out.println("Doctor assigned to appointment ID: " + appointmentId);
        } else {
            System.out.println("Invalid appointment or doctor ID.");
        }

        return "redirect:/admin/admin_dashboard";
    }

    // View all admins
    @GetMapping("/view-admins")
    public String viewAdmins(Model model) {
        List<User> admins = userRepository.findAll()
                .stream()
                .filter(user -> "ADMIN".equals(user.getRole()))
                .collect(Collectors.toList());

        model.addAttribute("admins", admins);
        return "view_admins";
    }

    // Show form to add new admin
    @GetMapping("/add-admin")
    public String showAdminForm() {
        return "add_admin";
    }

    // Process new admin form
    @PostMapping("/add-admin")
    public String addAdmin(@RequestParam String email, @RequestParam String password, @RequestParam String fullname) {
        User admin = new User(email, passwordEncoder.encode(password), fullname, "ADMIN");
        userRepository.save(admin);
        return "redirect:/admin/view-admins";
    }
}
