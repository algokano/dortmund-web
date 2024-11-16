package de.fhdo.fintrack.repositories;

import de.fhdo.fintrack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}