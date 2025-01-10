package de.fhdo.fintrack.services;

import de.fhdo.fintrack.entities.Budget;
import de.fhdo.fintrack.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
}
