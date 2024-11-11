package OnlineBankingSystem;

// OnlineBankingSystem.java
// Author: Radhakrishan

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String accountHolderName;
    private String accountNumber;
    private String userId;
    private String pin;
    private double balance;

    // Constructor
    public Account(String accountHolderName, String accountNumber, String userId, String pin, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }

    // Check if user ID and PIN match
    public boolean authenticate(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    // Get account balance
    public double getBalance() {
        return balance;
    }

    // Deposit funds
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw funds
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrawn: $" + amount);
        } else {
            System.out.println("Invalid or insufficient balance.");
        }
    }

    // Display account details
    public void displayAccountInfo() {
        System.out.println("\nAccount Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("User ID: " + userId);
        System.out.println("Current Balance: $" + balance);
    }
}

public class OnlineBankingSystem {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String ADMIN_USER_ID = "admin";
    private static final String ADMIN_PASSWORD = "admin1234";

    public static void main(String[] args) {
        System.out.println("Welcome to the Online Banking System");

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Register as New User");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    loginAdmin();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using the Online Banking System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter an account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Create a user ID: ");
        String userId = scanner.nextLine();

        System.out.print("Create a PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Account newAccount = new Account(name, accountNumber, userId, pin, initialBalance);
        accounts.put(userId, newAccount);
        System.out.println("Account registered successfully!");
    }

    private static void loginUser() {
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        Account account = accounts.get(userId);

        if (account != null && account.authenticate(userId, pin)) {
            System.out.println("Login successful!");
            manageAccount(account);
        } else {
            System.out.println("Invalid User ID or PIN. Please try again.");
        }
    }

    private static void loginAdmin() {
        System.out.print("Enter Admin User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (userId.equals(ADMIN_USER_ID) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful!");
            manageAdmin();
        } else {
            System.out.println("Invalid Admin User ID or Password. Please try again.");
        }
    }

    private static void manageAccount(Account account) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. View Account Details");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit Funds");
            System.out.println("4. Withdraw Funds");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    account.displayAccountInfo();
                    break;
                case 2:
                    System.out.println("Current Balance: $" + account.getBalance());
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageAdmin() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Accounts");
            System.out.println("2. View Account by User ID");
            System.out.println("3. Deposit into Account by User ID");
            System.out.println("4. Withdraw from Account by User ID");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    for (Account account : accounts.values()) {
                        account.displayAccountInfo();
                    }
                    break;
                case 2:
                    viewAccountById();
                    break;
                case 3:
                    depositIntoAccountById();
                    break;
                case 4:
                    withdrawFromAccountById();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAccountById() {
        System.out.print("Enter User ID to view account details: ");
        String userId = scanner.nextLine();
        Account account = accounts.get(userId);
        if (account != null) {
            account.displayAccountInfo();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void depositIntoAccountById() {
        System.out.print("Enter User ID to deposit funds: ");
        String userId = scanner.nextLine();
        Account account = accounts.get(userId);
        if (account != null) {
            System.out.print("Enter amount to deposit: $");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawFromAccountById() {
        System.out.print("Enter User ID to withdraw funds: ");
        String userId = scanner.nextLine();
        Account account = accounts.get(userId);
        if (account != null) {
            System.out.print("Enter amount to withdraw: $");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }
}
