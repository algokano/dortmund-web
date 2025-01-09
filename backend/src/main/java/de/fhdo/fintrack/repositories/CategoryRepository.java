package de.fhdo.fintrack.repositories;

import de.fhdo.fintrack.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}