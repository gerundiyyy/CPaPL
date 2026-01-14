package org.example;

import java.sql.*;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentDatabaseManager {

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Academy?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Pipik091!";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Scanner scanner;

    static {
        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error loading MySQL JDBC Driver");
            System.err.println("Please add MySQL connector to your project dependencies");
            System.exit(1);
        }
    }

    public StudentDatabaseManager() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        StudentDatabaseManager manager = new StudentDatabaseManager();
        manager.run();
    }

    /**
     * Main program loop
     */
    private void run() {
        try {
            System.out.println("\n══════════════════════════════════════════════");
            System.out.println("     ACADEMY STUDENT DATABASE MANAGER");
            System.out.println("══════════════════════════════════════════════\n");

            // Test database connection
            if (!testConnection()) {
                System.err.println("Cannot continue without database connection");
                return;
            }

            // Main menu loop
            boolean running = true;
            while (running) {
                displayMenu();
                int choice = getMenuChoice();

                switch (choice) {
                    case 1 -> displayAllStudents();
                    case 2 -> searchStudentsByBirthMonth();
                    case 3 -> addNewStudent();
                    case 4 -> displayStatistics();
                    case 5 -> searchStudentsByLastName();
                    case 0 -> {
                        System.out.println("\nThank you for using Academy Student Manager. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("\nInvalid choice. Please try again.");
                }

                if (running && choice != 0) {
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            }

        } catch (SQLException e) {
            System.err.println("\nDatabase error occurred:");
            System.err.println("Message: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
        } finally {
            scanner.close();
        }
    }

    /**
     * Displays the main menu
     */
    private void displayMenu() {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("                   MAIN MENU");
        System.out.println("══════════════════════════════════════════════");
        System.out.println("1. Display All Students");
        System.out.println("2. Search Students by Birth Month");
        System.out.println("3. Add New Student");
        System.out.println("4. Display Statistics");
        System.out.println("5. Search Students by Last Name");
        System.out.println("0. Exit");
        System.out.println("══════════════════════════════════════════════");
        System.out.print("Enter your choice (0-5): ");
    }

    /**
     * Gets valid menu choice from user
     */
    private int getMenuChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (choice >= 0 && choice <= 5) {
                    return choice;
                } else {
                    System.out.print("Please enter a number between 0 and 5: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Tests database connection
     */
    private boolean testConnection() {
        try (Connection conn = getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("✓ Connected to database successfully");
            System.out.println("  Database: " + meta.getDatabaseProductName());
            System.out.println("  Version: " + meta.getDatabaseProductVersion());
            System.out.println();
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Connection failed: " + e.getMessage());
            System.err.println("\nTroubleshooting steps:");
            System.err.println("1. Ensure MySQL server is running");
            System.err.println("2. Check database 'Academy' exists");
            System.err.println("3. Verify username and password");
            System.err.println("4. Create database using provided SQL script");
            return false;
        }
    }

    /**
     * Gets database connection
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    /**
     * Displays all students in database
     */
    private void displayAllStudents() throws SQLException {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("            ALL STUDENTS IN ACADEMY");
        System.out.println("══════════════════════════════════════════════");

        String query = """
            SELECT 
                student_id, 
                first_name, 
                last_name, 
                birth_date, 
                email, 
                phone, 
                class,
                TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) as age,
                enrollment_date
            FROM Students 
            ORDER BY last_name, first_name
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            printStudentsTable(rs);
        }
    }

    /**
     * Searches for students born in specific month
     */
    private void searchStudentsByBirthMonth() throws SQLException {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("        SEARCH STUDENTS BY BIRTH MONTH");
        System.out.println("══════════════════════════════════════════════");

        System.out.println("\nAvailable months:");
        for (int i = 1; i <= 12; i++) {
            System.out.printf("%2d. %s%n", i, Month.of(i));
        }

        System.out.print("\nEnter month number (1-12): ");
        int month;

        while (true) {
            try {
                month = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (month >= 1 && month <= 12) {
                    break;
                } else {
                    System.out.print("Please enter a number between 1 and 12: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear invalid input
            }
        }

        String monthName = Month.of(month).toString();
        System.out.printf("\nSearching for students born in %s...\n", monthName);

        String query = """
            SELECT 
                student_id, 
                first_name, 
                last_name, 
                birth_date, 
                email, 
                phone, 
                class,
                TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) as age,
                enrollment_date
            FROM Students 
            WHERE MONTH(birth_date) = ?
            ORDER BY DAY(birth_date), last_name
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.printf("\nNo students found born in %s.%n", monthName);
                } else {
                    System.out.printf("\n╔══════════════════════════════════════════════════════════════════╗");
                    System.out.printf("\n║               STUDENTS BORN IN %-10s                    ║", monthName.toUpperCase());
                    System.out.printf("\n╚══════════════════════════════════════════════════════════════════╝\n");
                    printStudentsTable(rs);
                }
            }
        }
    }

    /**
     * Adds a new student to database
     */
    private void addNewStudent() throws SQLException {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("              ADD NEW STUDENT");
        System.out.println("══════════════════════════════════════════════");

        System.out.print("\nEnter first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter birth date (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter class (e.g., 10A): ");
        String studentClass = scanner.nextLine().trim();

        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

        String query = """
            INSERT INTO Students 
            (first_name, last_name, birth_date, email, phone, class, address, enrollment_date)
            VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE())
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, birthDate);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, studentClass);
            pstmt.setString(7, address);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\n✓ Student added successfully!");

                // Display the added student
                String selectQuery = """
                    SELECT * FROM Students 
                    WHERE email = ? 
                    ORDER BY student_id DESC 
                    LIMIT 1
                    """;

                try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                    selectStmt.setString(1, email);
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("\nAdded student details:");
                            System.out.println("────────────────────────────────────────────");
                            System.out.printf("ID: %d%n", rs.getInt("student_id"));
                            System.out.printf("Name: %s %s%n", rs.getString("first_name"), rs.getString("last_name"));
                            System.out.printf("Birth Date: %s%n", rs.getDate("birth_date"));
                            System.out.printf("Class: %s%n", rs.getString("class"));
                            System.out.printf("Email: %s%n", rs.getString("email"));
                            System.out.printf("Enrollment Date: %s%n", rs.getDate("enrollment_date"));
                        }
                    }
                }
            } else {
                System.out.println("\n✗ Failed to add student.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("\n✗ Error: Email already exists in database.");
        }
    }

    /**
     * Displays database statistics
     */
    private void displayStatistics() throws SQLException {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("            DATABASE STATISTICS");
        System.out.println("══════════════════════════════════════════════");

        String query = """
            SELECT 
                COUNT(*) as total_students,
                COUNT(DISTINCT class) as total_classes,
                MIN(birth_date) as oldest_birth,
                MAX(birth_date) as youngest_birth,
                AVG(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) as avg_age,
                MIN(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) as min_age,
                MAX(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) as max_age,
                (SELECT class FROM Students GROUP BY class ORDER BY COUNT(*) DESC LIMIT 1) as largest_class,
                (SELECT COUNT(*) FROM Students GROUP BY class ORDER BY COUNT(*) DESC LIMIT 1) as largest_class_count
            FROM Students
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int totalStudents = rs.getInt("total_students");
                int totalClasses = rs.getInt("total_classes");
                Date oldestBirth = rs.getDate("oldest_birth");
                Date youngestBirth = rs.getDate("youngest_birth");
                double avgAge = rs.getDouble("avg_age");
                int minAge = rs.getInt("min_age");
                int maxAge = rs.getInt("max_age");
                String largestClass = rs.getString("largest_class");
                int largestClassCount = rs.getInt("largest_class_count");

                System.out.println("\n📊 ACADEMY STATISTICS:");
                System.out.println("────────────────────────────────────────────");
                System.out.printf("• Total Students: %d%n", totalStudents);
                System.out.printf("• Number of Classes: %d%n", totalClasses);
                System.out.printf("• Average Age: %.1f years%n", avgAge);
                System.out.printf("• Age Range: %d - %d years%n", minAge, maxAge);
                System.out.printf("• Oldest Student Birth Date: %s%n", oldestBirth);
                System.out.printf("• Youngest Student Birth Date: %s%n", youngestBirth);
                System.out.printf("• Largest Class: %s (%d students)%n", largestClass, largestClassCount);

                // Monthly birth distribution
                System.out.println("\n📅 BIRTH MONTH DISTRIBUTION:");
                System.out.println("────────────────────────────────────────────");

                String monthQuery = """
                    SELECT 
                        MONTH(birth_date) as month_num,
                        COUNT(*) as student_count
                    FROM Students
                    GROUP BY MONTH(birth_date)
                    ORDER BY MONTH(birth_date)
                    """;

                try (Statement monthStmt = conn.createStatement();
                     ResultSet monthRs = monthStmt.executeQuery(monthQuery)) {

                    while (monthRs.next()) {
                        int monthNum = monthRs.getInt("month_num");
                        int studentCount = monthRs.getInt("student_count");
                        String monthName = Month.of(monthNum).toString();
                        System.out.printf("  %-15s: %d students%n", monthName, studentCount);
                    }
                }
            }
        }
    }

    /**
     * Searches students by last name
     */
    private void searchStudentsByLastName() throws SQLException {
        System.out.println("\n══════════════════════════════════════════════");
        System.out.println("         SEARCH STUDENTS BY LAST NAME");
        System.out.println("══════════════════════════════════════════════");

        System.out.print("\nEnter last name (or part of it): ");
        String lastNameSearch = scanner.nextLine().trim();

        String query = """
            SELECT 
                student_id, 
                first_name, 
                last_name, 
                birth_date, 
                email, 
                phone, 
                class,
                TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) as age,
                enrollment_date
            FROM Students 
            WHERE last_name LIKE ?
            ORDER BY last_name, first_name
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + lastNameSearch + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.printf("\nNo students found with last name containing '%s'.%n", lastNameSearch);
                } else {
                    System.out.printf("\n╔══════════════════════════════════════════════════════════════════╗");
                    System.out.printf("\n║          SEARCH RESULTS FOR: '%-20s'                  ║", lastNameSearch.toUpperCase());
                    System.out.printf("\n╚══════════════════════════════════════════════════════════════════╝\n");
                    printStudentsTable(rs);
                }
            }
        }
    }

    /**
     * Prints students table in formatted output
     */
    private void printStudentsTable(ResultSet rs) throws SQLException {
        int rowCount = 0;

        System.out.println("┌──────┬─────────────────────┬─────────────────────┬────────────┬──────────┬────────────┐");
        System.out.println("│  ID  │ Name                │ Birth Date          │    Age     │  Class   │ Enrollment │");
        System.out.println("├──────┼─────────────────────┼─────────────────────┼────────────┼──────────┼────────────┤");

        while (rs.next()) {
            int studentId = rs.getInt("student_id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            Date birthDate = rs.getDate("birth_date");
            int age = rs.getInt("age");
            String studentClass = rs.getString("class");
            Date enrollmentDate = rs.getDate("enrollment_date");

            String fullName = firstName + " " + lastName;
            if (fullName.length() > 19) {
                fullName = fullName.substring(0, 16) + "...";
            }

            System.out.printf("│ %4d │ %-19s │ %-19s │ %10d │ %-8s │ %-10s │%n",
                    studentId, fullName, birthDate, age, studentClass, enrollmentDate);
            rowCount++;
        }

        System.out.println("└──────┴─────────────────────┴─────────────────────┴────────────┴──────────┴────────────┘");
        System.out.printf("Total students displayed: %d%n", rowCount);
    }
}