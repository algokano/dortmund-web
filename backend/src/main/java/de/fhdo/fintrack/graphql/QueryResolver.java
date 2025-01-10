package de.fhdo.fintrack.graphql;

import de.fhdo.fintrack.entities.Category;
import de.fhdo.fintrack.entities.Transaction;
import de.fhdo.fintrack.entities.User;
import de.fhdo.fintrack.services.CategoryService;
import de.fhdo.fintrack.services.TransactionService;
import de.fhdo.fintrack.services.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class QueryResolver {

    private final UserService userService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public QueryResolver(UserService userService, TransactionService transactionService, CategoryService categoryService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public User getUserById(@Argument Long id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @QueryMapping
    public Transaction getTransactionById(@Argument Long id) {
        return transactionService.getTransactionById(id);
    }

    @QueryMapping
    public List<Transaction> getTransactionsByUserId(@Argument Long userId) {
        System.out.println("Fetching transactions for user ID: " + userId);
        return transactionService.getTransactionsByUserId(userId);
    }

    @QueryMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @QueryMapping
    public Category getCategoryById(@Argument Long id) {
        return categoryService.getCategoryById(id);
    }
}
