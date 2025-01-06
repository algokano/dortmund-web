# FinTrack Application

## Overview
FinTrack is a financial tracking application developed as part of our Web Technologies Lab assignments. The application includes features for user management, transaction management, and data visualization. It uses Spring Boot for the backend and Thymeleaf for templating, with GraphQL support for data querying.

## Team Information
**Team Name:** RKJS

**Team Members:**
- Onyedikachi Onah
- Sarvar Abdurakhimov
- Roamilkumar Kachchhi
- Junaid Ahmad

**Git Repository URL:** [https://git.inf.fh-dortmund.de/01/rokac001/web2-ws24-rkjs.git](https://git.inf.fh-dortmund.de/01/rokac001/web2-ws24-rkjs.git)

**PROJECT DIARY:** [https://docs.google.com/spreadsheets/d/116i_utucOQmKisKY16kUCmlCBGn4R0Im4D5A5mzhsoI/edit?usp=sharing](https://docs.google.com/spreadsheets/d/116i_utucOQmKisKY16kUCmlCBGn4R0Im4D5A5mzhsoI/edit?usp=sharing)

## Features
- User Management (Registration, Login, Update, Delete)
- Transaction Management (CRUD operations)
- GraphQL API for advanced data querying
- AJAX support for dynamic content rendering

## Requirements
To run this project, you will need:
- Java 21
- Maven 3.8+
- Spring Boot 3.3.5
- Node.js (optional, for frontend integration)

### Dependencies
The application includes the following dependencies:
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-graphql`
- `h2` (In-memory database for testing)
- `graphql-java-tools`

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://git.inf.fh-dortmund.de/01/rokac001/web2-ws24-rkjs.git
   cd backend
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
    - **GraphQL Playground:** [http://localhost:8080/graphql](http://localhost:8080/graphql)
    - **Thymeleaf Pages:** [http://localhost:8080](http://localhost:8080)

## API Endpoints
### REST Endpoints
1. **User Management:**
    - `POST /api/users` - Create a new user
    - `POST /api/signin` - Sign in with email and password
    - `GET /api/users` - Fetch all users
    - `GET /api/users/{id}` - Fetch a user by ID
    - `PUT /api/users/{id}` - Update user details
    - `DELETE /api/users/{id}` - Delete a user

2. **Transaction Management:**
    - `POST /api/transactions` - Create a new transaction
    - `GET /api/transactions` - Fetch all transactions
    - `GET /api/transactions/{id}` - Fetch a transaction by ID
    - `PUT /api/transactions/{id}` - Update transaction details
    - `DELETE /api/transactions/{id}` - Delete a transaction

### GraphQL Schema
#### Query
```graphql
type Query {
    getAllUsers: [User]
    getUserById(id: ID!): User
    getAllTransactions: [Transaction]
    getTransactionsByUserId(userId: ID!): [Transaction]
}
```

#### Mutation
```graphql
type Mutation {
    createUser(email: String!, username: String!, password: String!): User
    updateUser(id: ID!, email: String, username: String, password: String): User
    deleteUser(id: ID!): String

    createTransaction(userId: ID!, amount: Float!, category: String!, type: String!): Transaction
    updateTransaction(id: ID!, amount: Float, category: String, type: String): Transaction
    deleteTransaction(id: ID!): String
}
```

#### Types
```graphql
type User {
    id: ID!
    email: String!
    username: String!
}

type Transaction {
    id: ID
    amount: Float
    date: String
    category: String
    type: String
    user: User
}
```

### Example Queries
#### Fetch All Users
```graphql
query {
    getAllUsers {
        id
        email
        username
    }
}
```

#### Fetch All Transactions
```graphql
query {
    getAllTransactions {
        id
        amount
        category
        type
    }
}
```

## Project Structure
```
backend/
├── src/main/java/de/fhdo/fintrack
│   ├── config
│   │   └── CorsConfig.java
│   ├── controllers
│   │   ├── DashboardController.java
│   │   ├── LoginController.java
│   │   ├── TransactionController.java
│   │   └── UserController.java
│   ├── dto
│   │   └── UserDTO.java
│   ├── entities
│   │   ├── Transaction.java
│   │   └── User.java
│   ├── graphql
│   │   ├── MutationResolver.java
│   │   └── QueryResolver.java
│   ├── repositories
│   │   ├── TransactionRepository.java
│   │   └── UserRepository.java
│   ├── services
│   │   ├── TransactionService.java
│   │   └── UserService.java
│   └── FinTrackApplication.java
└── resources
    ├── graphql
    │   └── schema.graphqls
    ├── static
    │   ├── css
    │   │   └── styles.css
    │   └── js
    │       └── script.js
    └── templates
        ├── users.html
        └── transactions.html
```
