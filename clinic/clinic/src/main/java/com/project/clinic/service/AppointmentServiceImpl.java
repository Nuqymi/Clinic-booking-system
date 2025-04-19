package com.project.clinic.service;

import com.project.clinic.model.Appointment;
import com.project.clinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByUser(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    @Override
    public void cancelAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
