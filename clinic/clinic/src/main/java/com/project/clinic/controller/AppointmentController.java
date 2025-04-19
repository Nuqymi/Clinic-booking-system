package com.project.clinic.controller;

import com.project.clinic.model.Appointment;
import com.project.clinic.model.Doctor;
import com.project.clinic.model.User;
import com.project.clinic.service.AppointmentService;
import com.project.clinic.service.DoctorService;
import com.project.clinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService; // ✅ DoctorService needed to fetch Doctor object

    @GetMapping
    public String viewAppointments(Model model, Principal principal) {
        // Get logged-in user by email
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Get user's appointments
        List<Appointment> appointments = appointmentService.getAppointmentsByUser(user.getId());

        // Add data to the model for Thymeleaf
        model.addAttribute("appointments", appointments);

        return "appointments"; // ✅ Renders `appointments.html`
    }

    @PostMapping("/book")
    public String bookAppointment(@RequestParam Long userId,
                                  @RequestParam String day,
                                  @RequestParam String time,
                                  @RequestParam Long doctorId) {
        User user = userService.getUserById(userId);
        Doctor doctor = doctorService.getDoctorById(doctorId); // ✅ Get Doctor object

        // Convert date and time
        LocalDate appointmentDate = LocalDate.parse(day);
        LocalTime appointmentTime = LocalTime.parse(time);

        // ✅ Set default booking fee
        double baseFee = 50.0;
        double extraCharge = 0.0;

        // ✅ Add RM 10 if booking after 5 PM
        if (appointmentTime.isAfter(LocalTime.of(17, 0))) {
            extraCharge = 10.0;
        }

        double totalFee = baseFee + extraCharge;

        // ✅ Create appointment with Doctor object
        Appointment appointment = new Appointment(user, appointmentDate, appointmentTime, doctor, totalFee);
        appointmentService.createAppointment(appointment);

        // ✅ Redirect to calculation page with the appointment ID
        return "redirect:/calculate?appointmentId=" + appointment.getId();
    }
}
