package com.project.clinic.controller;

import com.project.clinic.model.Appointment;
import com.project.clinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CalculationController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/calculate")
    public String showCalculation(@RequestParam("appointmentId") Long appointmentId, Model model) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
    
        if (optionalAppointment.isEmpty()) {
            model.addAttribute("error", "Appointment not found!");
            return "error"; // Redirect to an error page
        }
    
        Appointment appointment = optionalAppointment.get();
    
        model.addAttribute("appointment", appointment);
        model.addAttribute("baseFee", 50.0);
        model.addAttribute("extraCharge", appointment.getFee() - 50.0);
        model.addAttribute("totalFee", appointment.getFee());

        return "calculation"; // Load the calculation page
    }
}
