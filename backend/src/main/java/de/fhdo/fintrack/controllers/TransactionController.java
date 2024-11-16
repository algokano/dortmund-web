package de.fhdo.fintrack.controllers;

import de.fhdo.fintrack.entities.Transaction;
import de.fhdo.fintrack.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String getTransactions(Model model) {
        // Fetch data from the service
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions"; // Thymeleaf template name
    }
}