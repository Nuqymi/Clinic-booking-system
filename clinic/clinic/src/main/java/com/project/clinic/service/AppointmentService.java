package com.project.clinic.service;

import com.project.clinic.model.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);
    List<Appointment> getAppointmentsByUser(Long userId);
    void cancelAppointment(Long id);
}
