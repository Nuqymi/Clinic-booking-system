package com.project.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.clinic.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
