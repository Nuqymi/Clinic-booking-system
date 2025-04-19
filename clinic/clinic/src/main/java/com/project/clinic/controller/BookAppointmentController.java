package com.project.clinic.controller;

import com.project.clinic.model.Appointment;
import com.project.clinic.model.User;
import com.project.clinic.repository.AppointmentRepository;
import com.project.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
public class BookAppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/book-appointment")
    public String showBookingForm() {
        return "book_appointment"; // Load the booking form page
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(@RequestParam("date") String date,
                                  @RequestParam("time") String time,
                                  Model model) {
        // Get the currently logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
    
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "book_appointment"; // Show error message on the booking form
        }
    
        User user = optionalUser.get();
    
        // Convert date and time to appropriate formats
        LocalDate appointmentDate = LocalDate.parse(date);
        LocalTime appointmentTime = LocalTime.parse(time);
    
        // Default booking fee
        double baseFee = 50.0;
        double extraCharge = 0.0;
    
        // Extra charge if appointment is after 5 PM
        if (appointmentTime.isAfter(LocalTime.of(17, 0))) {
            extraCharge = 10.0;
        }
    
        double totalFee = baseFee + extraCharge;
    
        // Create a new appointment with doctorId set as null
        Appointment appointment = new Appointment(user, appointmentDate, appointmentTime, null, totalFee);
        appointmentRepository.save(appointment);
    
        // Redirect to the calculation page
        return "redirect:/calculate?appointmentId=" + appointment.getId();
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success"; // Load the success page after booking confirmation
    }
}
