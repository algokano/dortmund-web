package de.fhdo.fintrack;

import de.fhdo.fintrack.entities.Category;
import de.fhdo.fintrack.entities.Transaction;
import de.fhdo.fintrack.entities.User;
import de.fhdo.fintrack.repositories.CategoryRepository;
import de.fhdo.fintrack.repositories.TransactionRepository;
import de.fhdo.fintrack.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class FinTrackApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinTrackApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository, TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
		return args -> {
			// Initialize categories
			Category food = new Category();
			food.setName("Food");
			food.setDescription("Expenses related to food and dining");
			categoryRepository.save(food);

			Category transport = new Category();
			transport.setName("Transport");
			transport.setDescription("Expenses related to transport and travel");
			categoryRepository.save(transport);

			Category entertainment = new Category();
			entertainment.setName("Entertainment");
			entertainment.setDescription("Expenses related to entertainment");
			categoryRepository.save(entertainment);

			// Create a user
			User user = new User();
			user.setUsername("testuser");
			user.setPassword("password");
			user.setEmail("testuser@example.com");
			userRepository.save(user);

			// Create a transaction
			Transaction transaction = new Transaction();
			transaction.setAmount(new BigDecimal("100.50"));
			transaction.setDate(LocalDateTime.now());
			transaction.setCategory(food); // Link category
			transaction.setType("Expense");
			transaction.setUser(user);
			transactionRepository.save(transaction);

			// Verify data
			userRepository.findAll().forEach(System.out::println);
			transactionRepository.findAll().forEach(System.out::println);
		};
	}
}