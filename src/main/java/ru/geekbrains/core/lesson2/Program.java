package ru.geekbrains.core.lesson2;
import java.util.InputMismatchException;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';

    private static final int WIN_COUNT = 4;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static  char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;


    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkGameState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkGameState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля
     */
    static void initialize(){
        fieldSizeY = 5;
        fieldSizeX = 5;

        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++){
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    private static void printField(){
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++){
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int y = 0; y < fieldSizeY; y++){
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++){
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
    static void humanTurn() {
        int x;
        int y;

        do {
            try {
                System.out.print("Введите координаты хода X и Y (от 1 до " + fieldSizeX + ")\nчерез пробел: ");
                x = scanner.nextInt() - 1;
                y = scanner.nextInt() - 1;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите числа.");
                scanner.next(); // Очистка буфера сканнера
                x = y = -1; // Помечаем некорректный ввод
            }
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
    }

    /**
     * Ход игрока (компьютера)
     */
    static void aiTurn() {
        int x = -1;
        int y = -1;

        // Проверяем каждую свободную ячейку
        for (int row = 0; row < fieldSizeY; row++) {
            for (int col = 0; col < fieldSizeX; col++) {
                if (isCellEmpty(col, row)) {
                    field[row][col] = DOT_HUMAN; // Подставляем фишку игрока
                    if (checkWin(DOT_HUMAN)) {
                        // Если это приводит к победе игрока, блокируем ход компьютера в эту ячейку
                        x = col;
                        y = row;
                    }
                    field[row][col] = DOT_EMPTY; // Возвращаем ячейке пустое значение после проверки
                }
            }
        }

        // Если есть выигрышный ход для компьютера, ставим туда фишку
        if (x != -1 && y != -1) {
            field[y][x] = DOT_AI;
        } else {
            // В противном случае, делаем случайный ход
            do {
                x = random.nextInt(fieldSizeX);
                y = random.nextInt(fieldSizeY);
            } while (!isCellEmpty(x, y));

            field[y][x] = DOT_AI;
        }
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y){
        return field[y][x] == DOT_EMPTY;
    }

    /**
     * Проверка доступности ячейки игрового поля
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x< fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /**
     * Метод проверки состояния игры
     * @param dot фишка игрока
     * @param s победный слоган
     * @return результат проверки состояния игры
     */
    static boolean checkGameState(char dot, String s){
        if (checkWin(dot)){
            System.out.println(s);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        for (int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++){
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, достиг ли игрок победы.
     *
     * @param dot Фишка игрока.
     * @return {@code true}, если игрок выиграл, иначе {@code false}.
     */
    static boolean checkWin(char dot) {
        return checkHorizontalWin(dot) || checkVerticalWin(dot) || checkDiagonalWin(dot);
    }

    /**
     * Проверяет победу по горизонталям.
     *
     * @param dot Фишка игрока.
     * @return {@code true}, если есть победа по горизонталям, иначе {@code false}.
     */
    private static boolean checkHorizontalWin(char dot) {
        for (int row = 0; row < fieldSizeY; row++) {
            boolean win = true;
            for (int col = 0; col < fieldSizeX; col++) {
                if (field[row][col] != dot) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    /**
     * Проверяет победу по вертикалям.
     *
     * @param dot Фишка игрока.
     * @return {@code true}, если есть победа по вертикалям, иначе {@code false}.
     */
    private static boolean checkVerticalWin(char dot) {
        for (int col = 0; col < fieldSizeX; col++) {
            boolean win = true;
            for (int row = 0; row < fieldSizeY; row++) {
                if (field[row][col] != dot) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    /**
     * Проверяет победу по диагоналям.
     *
     * @param dot Фишка игрока.
     * @return {@code true}, если есть победа по диагоналям, иначе {@code false}.
     */

    // Проверка главной диагонали
    private static boolean checkDiagonalWin(char dot) {
        boolean mainDiagonalWin = true;
        for (int i = 0; i < Math.min(fieldSizeX, fieldSizeY); i++) {
            if (field[i][i] != dot) {
                mainDiagonalWin = false;
                break;
            }
        }
        if (mainDiagonalWin) return true;

        boolean antiDiagonalWin = true;
        for (int i = 0; i < Math.min(fieldSizeX, fieldSizeY); i++) {
            if (field[i][fieldSizeX - 1 - i] != dot) {
                antiDiagonalWin = false;
                break;
            }
        }
        return antiDiagonalWin;
    }



    static boolean check1(int x, int y, char dot, int winCount){
        return false;
    }

    static boolean check2(int x, int y, char dot, int winCount){
        return false;
    }

    static boolean check3(int x, int y, char dot, int winCount){
        return false;
    }

    static boolean check4(int x, int y, char dot, int winCount){
        return false;
    }



}
