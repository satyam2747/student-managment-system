import java.io.*;
import java.util.*;

class Student implements Serializable {
    int id;
    String name;
    int age;

    Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

public class StudentManagement {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "students.dat";

    // Load data from file
    static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (Exception e) {
            students = new ArrayList<>();
        }
    }

    // Save data to file
    static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (Exception e) {
            System.out.println("Error saving data!");
        }
    }

    // Add Student
    static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            for (Student s : students) {
                if (s.id == id) {
                    System.out.println("ID already exists!");
                    return;
                }
            }

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            students.add(new Student(id, name, age));
            saveToFile();

            System.out.println("✅ Student Added Successfully!");
        } catch (Exception e) {
            System.out.println("❌ Invalid Input!");
            sc.nextLine();
        }
    }

    // View Students
    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("⚠ No Students Found!");
            return;
        }

        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println("ID: " + s.id + " | Name: " + s.name + " | Age: " + s.age);
        }
    }

    // Search Student
    static void searchStudent() {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("✅ Found → Name: " + s.name + ", Age: " + s.age);
                return;
            }
        }

        System.out.println("❌ Student Not Found!");
    }

    // Delete Student
    static void deleteStudent() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        Iterator<Student> it = students.iterator();

        while (it.hasNext()) {
            Student s = it.next();
            if (s.id == id) {
                it.remove();
                saveToFile();
                System.out.println("🗑 Student Deleted!");
                return;
            }
        }

        System.out.println("❌ Student Not Found!");
    }

    // Update Student
    static void updateStudent() {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.id == id) {
                System.out.print("Enter New Name: ");
                s.name = sc.nextLine();

                System.out.print("Enter New Age: ");
                s.age = sc.nextInt();

                saveToFile();
                System.out.println("🔄 Student Updated!");
                return;
            }
        }

        System.out.println("❌ Student Not Found!");
    }

    // Main Menu
    public static void main(String[] args) {

        loadFromFile();

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Update Student");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("❌ Invalid Input!");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewStudents(); break;
                case 3: searchStudent(); break;
                case 4: deleteStudent(); break;
                case 5: updateStudent(); break;
                case 6:
                    System.out.println("👋 Exiting...");
                    System.exit(0);
                default:
                    System.out.println("⚠ Invalid Choice!");
            }
        }
    }
}