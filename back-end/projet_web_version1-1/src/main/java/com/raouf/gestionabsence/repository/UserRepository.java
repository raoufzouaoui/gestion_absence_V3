package com.raouf.gestionabsence.repository;

import java.util.Optional;

import com.raouf.gestionabsence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);

}
