package de.fhdo.fintrack.services;

import de.fhdo.fintrack.entities.Transaction;
import de.fhdo.fintrack.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

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
    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
        // Check if the transaction exists
        if (transactionRepository.existsById(transactionId)) {
            updatedTransaction.setId(transactionId); // Ensure the ID is set
            return transactionRepository.save(updatedTransaction); // Save the updated transaction
        } else {
            throw new RuntimeException("Transaction not found for update");
        }
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
}