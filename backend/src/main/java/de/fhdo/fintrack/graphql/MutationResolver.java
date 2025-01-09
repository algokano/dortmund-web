package de.fhdo.fintrack.graphql;

import de.fhdo.fintrack.entities.Category;
import de.fhdo.fintrack.entities.User;
import de.fhdo.fintrack.entities.Transaction;
import de.fhdo.fintrack.services.UserService;
import de.fhdo.fintrack.services.TransactionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class MutationResolver {

    private final UserService userService;
    private final TransactionService transactionService;

    public MutationResolver(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @MutationMapping
    public User createUser(@Argument String email,@Argument String username,@Argument String password) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        return userService.saveUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String email, @Argument String username, @Argument String password) {
        User user = userService.getUserById(id);
        if (email != null) user.setEmail(email);
        if (username != null) user.setUsername(username);
        if (password != null) user.setPassword(password);
        return userService.saveUser(user);
    }

    @MutationMapping
    public String deleteUser(@Argument Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @MutationMapping
    public Transaction createTransaction(@Argument Long userId, @Argument Float amount, @Argument Category category, @Argument String type) {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setCategory(category);
        transaction.setType(type);
        transaction.setUser(userService.getUserById(userId));
        return transactionService.saveTransaction(transaction);
    }

    @MutationMapping
    public Transaction updateTransaction(@Argument Long id, @Argument Float amount, @Argument Category category, @Argument String type) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (amount != null) transaction.setAmount(BigDecimal.valueOf(amount));
        if (category != null) transaction.setCategory(category);
        if (type != null) transaction.setType(type);
        return transactionService.updateTransaction(id, transaction);
    }

    @MutationMapping
    public String deleteTransaction(@Argument Long id) {
        transactionService.deleteTransaction(id);
        return "Transaction deleted successfully";
    }
}
