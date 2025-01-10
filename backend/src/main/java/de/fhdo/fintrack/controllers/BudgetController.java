package de.fhdo.fintrack.controllers;

import de.fhdo.fintrack.entities.Budget;
import de.fhdo.fintrack.entities.Category;
import de.fhdo.fintrack.repositories.BudgetRepository;
import de.fhdo.fintrack.repositories.CategoryRepository;
import de.fhdo.fintrack.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BudgetService budgetService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BudgetController(BudgetService budgetService, CategoryRepository categoryRepository) {
        this.budgetService = budgetService;
        this.categoryRepository = categoryRepository; // Assign the injected repository
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        // Validate the category ID
        Category category = categoryRepository.findById(budget.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // Set the category
        budget.setCategory(category);

        // Save the budget
        Budget savedBudget = budgetService.saveBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBudget);
    }

    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget updatedBudget) {
        return budgetRepository.findById(id)
                .map(existingBudget -> {
                    // Update basic fields
                    existingBudget.setName(updatedBudget.getName());
                    existingBudget.setAllocatedAmount(updatedBudget.getAllocatedAmount());
                    existingBudget.setStartDate(updatedBudget.getStartDate());
                    existingBudget.setEndDate(updatedBudget.getEndDate());

                    // Handle category update
                    if (updatedBudget.getCategory() != null && updatedBudget.getCategory().getId() != null) {
                        Category category = categoryRepository.findById(updatedBudget.getCategory().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
                        existingBudget.setCategory(category);
                    } else {
                        existingBudget.setCategory(null); // Set to null if no valid category is provided
                    }

                    return budgetRepository.save(existingBudget);
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
