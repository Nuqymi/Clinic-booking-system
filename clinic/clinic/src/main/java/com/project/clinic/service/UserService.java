package com.project.clinic.service;

import com.project.clinic.model.User;
import com.project.clinic.dto.UserDto;

public interface UserService {
    User findByEmail(String email);

    User save(UserDto userDto);

    User getUserById(Long id); // ✅ Added method
}
