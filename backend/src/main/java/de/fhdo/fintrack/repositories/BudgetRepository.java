package de.fhdo.fintrack.repositories;

import de.fhdo.fintrack.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
