package de.fhdo.fintrack.controllers;

import de.fhdo.fintrack.entities.Budget;
import de.fhdo.fintrack.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/budgets")
public class BudgetController {
    @Autowired
    private BudgetRepository budgetRepository;

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetRepository.save(budget);
    }

    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget updatedBudget) {
        return budgetRepository.findById(id)
                .map(budget -> {
                    budget.setName(updatedBudget.getName());
                    budget.setAllocatedAmount(updatedBudget.getAllocatedAmount());
                    budget.setStartDate(updatedBudget.getStartDate());
                    budget.setEndDate(updatedBudget.getEndDate());
                    budget.setCategory(updatedBudget.getCategory());
                    return budgetRepository.save(budget);
                })
                .orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id) {
        budgetRepository.deleteById(id);
    }

    @GetMapping("/{id}/progress")
    public Map<String, Object> getBudgetProgress(@PathVariable Long id) {
        return budgetRepository.findById(id)
                .map(budget -> {
                    double spentAmount = budget.getCategory().getTransactions().stream()
                            .filter(transaction -> {
                                LocalDate transactionDate = transaction.getDate().toLocalDate(); // Convert LocalDateTime to LocalDate
                                return !transactionDate.isBefore(budget.getStartDate()) &&
                                        !transactionDate.isAfter(budget.getEndDate());
                            })
                            .mapToDouble(transaction -> transaction.getAmount().doubleValue())
                            .sum();

                    double remainingAmount = budget.getAllocatedAmount() - spentAmount;
                    double percentage = (spentAmount / budget.getAllocatedAmount()) * 100;

                    Map<String, Object> progress = new HashMap<>();
                    progress.put("spentAmount", spentAmount);
                    progress.put("remainingAmount", remainingAmount);
                    progress.put("percentage", percentage);
                    return progress;
                })
                .orElseThrow(() -> new RuntimeException("Budget not found"));
    }
}
