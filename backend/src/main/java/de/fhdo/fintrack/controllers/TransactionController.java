package de.fhdo.fintrack.controllers;

import de.fhdo.fintrack.entities.Transaction;
import de.fhdo.fintrack.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/transactions") // Base path for all transaction-related routes
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Thymeleaf: Render Transactions Page
    @GetMapping
    public String getTransactionsPage(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions"; // Thymeleaf template name
    }

    // REST API: Get all transactions
    @GetMapping("/api")
    @ResponseBody
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // REST API: Get transactions by user ID
    @GetMapping("/api/user/{userId}")
    @ResponseBody
    public List<Transaction> getTransactionsByUserId(@PathVariable Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    // REST API: Create a new transaction
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    // REST API: Update a transaction
    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        Transaction updatedTransaction = transactionService.updateTransaction(transaction.getId(), transaction);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }

    // REST API: Delete a transaction
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}