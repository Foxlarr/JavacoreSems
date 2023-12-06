package ru.geekbrains.core.lesson2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Базовый класс Employee
abstract class Employee implements Comparable<Employee> {
    private String name;
    private double monthlySalary;

    public Employee(String name, double monthlySalary) {
        this.name = name;
        this.monthlySalary = monthlySalary;
    }

    public abstract double calculateAverageMonthlySalary();

    public String getName() {
        return name;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    @Override
    public int compareTo(Employee other) {
        // Реализация для сравнения сотрудников по именам
        return this.name.compareTo(other.name);
    }
}

// Первый потомок - Freelancer
class Freelancer extends Employee {
    private double hourlyRate;

    public Freelancer(String name, double hourlyRate) {
        super(name, 0); // У Freelancer фиксированная зарплата не требуется
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateAverageMonthlySalary() {
        return 20.8 * 8 * hourlyRate;
    }
}

// Второй потомок - Worker
class Worker extends Employee {
    public Worker(String name, double monthlySalary) {
        super(name, monthlySalary);
    }

    @Override
    public double calculateAverageMonthlySalary() {
        return getMonthlySalary();
    }
}

// Интерфейс для сравнения сотрудников по зарплате
interface SalaryComparator extends Comparator<Employee> {
    // Нет необходимости в явной декларации метода
}


// Главный класс, содержащий массив сотрудников
class EmployeeManager implements Iterable<Employee> {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void sortEmployees(Comparator<Employee> comparator) {
        Collections.sort(employees, comparator);
    }

    @Override
    public java.util.Iterator<Employee> iterator() {
        return employees.iterator();
    }
}

// Остальной код без изменений

// Пример использования
public class Main {
    public static void main(String[] args) {
        EmployeeManager employeeManager = new EmployeeManager();

        // Заполняем массив сотрудниками
        employeeManager.addEmployee(new Freelancer("John", 25));
        employeeManager.addEmployee(new Worker("Alice", 3000));
        employeeManager.addEmployee(new Freelancer("Bob", 30));

        // Создаем объект компаратора на основе интерфейса SalaryComparator
        Comparator<Employee> salaryComparator = Comparator.comparingDouble(Employee::calculateAverageMonthlySalary);

        // Сортировка сотрудников по зарплате
        employeeManager.sortEmployees(salaryComparator);

        // Вывод данных с использованием foreach
        for (Employee employee : employeeManager) {
            System.out.println(employee.getName() + ": " + employee.calculateAverageMonthlySalary());
        }
    }
}


