package de.fhdo.fintrack.services;

import de.fhdo.fintrack.entities.Category;
import de.fhdo.fintrack.entities.Transaction;
import de.fhdo.fintrack.entities.User;
import de.fhdo.fintrack.repositories.CategoryRepository;
import de.fhdo.fintrack.repositories.TransactionRepository;
import de.fhdo.fintrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Get transactions by user ID
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // Add a new transaction
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll(); // Fetches all transactions from the database
    }

    // Save a transaction (can be used for creating or updating)
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Update a transaction
    public Transaction updateTransaction(Transaction transaction, Long categoryId, Long userId) {
        if (!transactionRepository.existsById(transaction.getId())) {
            throw new RuntimeException("Transaction not found");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        transaction.setCategory(category);
        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Category category, Transaction updatedTransaction) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Update fields
        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setCategory(category);
        transaction.setType(updatedTransaction.getType());

        // Save and return
        return transactionRepository.save(transaction);
    }

    // Delete a transaction
    public void deleteTransaction(Long transactionId) {
        // Check if the transaction exists
        if (transactionRepository.existsById(transactionId)) {
            transactionRepository.deleteById(transactionId);
        } else {
            throw new RuntimeException("Transaction not found for deletion");
        }
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    public Transaction createTransaction(Transaction transaction, Long categoryId, Long userId) {
        // Fetch the category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Fetch the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set category and user in the transaction
        transaction.setCategory(category);
        transaction.setUser(user);

        // Save the transaction
        return transactionRepository.save(transaction);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}