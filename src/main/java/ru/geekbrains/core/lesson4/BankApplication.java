package ru.geekbrains.core.lesson4;


// Исключение для недостаточных средств
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Класс счета
class Account {
    private double balance;

    public Account(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма депозита не может быть отрицательной");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Недостаточно средств. Текущий баланс: " + balance);
        }
        balance -= amount;
    }
}

// Наследник класса Account - Кредитный счет
class CreditAccount extends Account {
    public CreditAccount(double initialBalance) {
        super(initialBalance);
    }
}

// Наследник класса Account - Дебетовый счет
class DebitAccount extends Account {
    public DebitAccount(double initialBalance) {
        super(initialBalance);
    }
}

// Класс транзакции
class Transaction {
    public static void transfer(Account source, Account destination, double amount) throws InsufficientFundsException {
        try {
            source.withdraw(amount);
            destination.deposit(amount);
        } catch (InsufficientFundsException e) {
            throw new InsufficientFundsException("Транзакция не выполнена. " + e.getMessage());
        }
    }
}

public class BankApplication {
    public static void main(String[] args) {
        try {
            // Пример использования
            DebitAccount account1 = new DebitAccount(1000);
            CreditAccount account2 = new CreditAccount(500);

            System.out.println("Исходные балансы:");
            System.out.println("Счет 1: " + account1.getBalance());
            System.out.println("Счет 2: " + account2.getBalance());

            // Попытка перевода средств
            try {
                Transaction.transfer(account1, account2, 700);
                System.out.println("Транзакция выполнена успешно.");
            } catch (InsufficientFundsException e) {
                System.out.println("Ошибка транзакции: " + e.getMessage());
            }

            // Вывод балансов после транзакции
            System.out.println("Балансы после транзакции:");
            System.out.println("Счет 1: " + account1.getBalance());
            System.out.println("Счет 2: " + account2.getBalance());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
