import java.util.Random;
import java.util.Scanner;

public class TickTackToe2 {

    // Блок настроек игры:
    private static char[][] map;
    private static int SIZE;
    private static int numberGame = 1;
    private static int amountSymbolsForWin = 0;

    private static int verticalCoordinate = 0;
    private static int horizontalCoordinate = 0;

    private static final char DOT_EMPTY = '•';
    private static final char DOT_X = 'x';
    private static final char DOT_O = '◯';

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        do {
            startGame();
            numberGame++;
        } while (isContinueGame());


    }

    /**
     * Метод грового процесса
     */
    private static void startGame() {

        System.out.println("Правила:на поле до 5х5 побеждают 3 символа, на поле до 7х7 - 4 символа, на поле до 10х10 - 5 символов, больше 10х10 - 6 символов! ");

        initMap();
        printMap();

        while (true) {
            humanTurn();
            if (isEndGame(DOT_X)) {
                break;
            }

            computerTurn();
            if (isEndGame(DOT_O)) {
                break;
            }
        }

        System.out.println("Игра закончена \n");
        System.out.println("Сыграем еще? y - yes/ n - no");


    }

    /**
     * Метод вычисления выигрышной комбинации.
     */
    public static int getAmountSymbolsForWin() {
        if (SIZE <= 5) {
            amountSymbolsForWin = 3;

        } else if (SIZE > 5 && SIZE < 8) {
            amountSymbolsForWin = 4;

        } else if (SIZE >= 8) {
            amountSymbolsForWin = 5;
        }
        else {
            amountSymbolsForWin = 6;
        }
        return amountSymbolsForWin;
    }

    /**
     * Метод запроса на продолжение игры
     */
    private static boolean isContinueGame() {
        String answer;
        answer = scanner.next();
        if (answer.equals("y") || answer.equals("yes")) {
            System.out.println("\n Игра №" + numberGame);
            return true;
        } else {
            System.out.println("До скорых встреч");
        }
        return false;
    }

    /**
     * Метод подготовки игрового поля
     */
    private static int initMap() {

        do {
            System.out.println("Введите размеры игрового поля (3....20)");
            SIZE = scanner.nextInt();

        } while (!isSizeValid(SIZE));

        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
        return SIZE;
    }

    /**
     * Метод вывода игрового поля на экран
     */
    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Ход человека
     */
    private static void humanTurn() {
        do {
            System.out.println("Введите координаты ячейки, через пробел: ");
            verticalCoordinate = scanner.nextInt() - 1;
            horizontalCoordinate = scanner.nextInt() - 1;
        } while (!isCellValid(verticalCoordinate, horizontalCoordinate));


        map[verticalCoordinate][horizontalCoordinate] = DOT_X;
    }

    /**
     * Метод валидации запрашиваемой ячейки на корректность
     *
     * @param x - координата по горизонтали
     * @param y - координата по вертикали
     * @return boolean - признак валидности
     */
    private static boolean isCellValid(int y, int x) {

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || map[y][x] != DOT_EMPTY) {
            System.out.println("Введены некорректные координаты!\n");
            return false;
        }
        return true;
    }

    /**
     * Метод валидации вводимого размера поля на корректность
     */
    private static boolean isSizeValid(int x) {

        if (x < 3 || x > 20) {
            return false;
        }
        return true;
    }

    /**
     * Метод проверки игры на завершение
     *
     * @param playerSymbol - символ которым играет текущий игрок
     * @return boolean - признак завершения игры
     */
    private static boolean isEndGame(char playerSymbol) {
        boolean result = false;
        printMap();
        // Проверяем необходимость следующего хода
        if (checkWin(playerSymbol)) {
            System.out.println("Победили: " + playerSymbol);
            result = true;
        }
        if (isMapFull()) {
            System.out.println("Ничья");
            result = true;
        }

        return result;
    }

    /**
     * Проверка на 100%-ую заполненность поля
     *
     * @return boolean - признак оптимальности
     */
    private static boolean isMapFull() {
        boolean result = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY)
                    result = false;
            }
        }
        return result;
    }

    /**
     * Метод проверки выигрыша
     *
     * @param playerSymbol - символ введенный пользователем
     * @return boolean - результат проверки
     */
    private static boolean checkWin(char playerSymbol) {

        getAmountSymbolsForWin();

        if (checkLine(playerSymbol)) {
            return true;
        }

        if (checkColumn(playerSymbol)) {
            return true;
        }

        if (checkLeftDiagonal(playerSymbol)) {
            return true;
        }

        if (checkRightDiagonal(playerSymbol)) {
            return true;
        }

        return false;
    }

    /**
     * Метод поиска символов игрока по строке.
     */
    public static boolean checkLine(char playerSymbol) {
        int winPoint = 0;

        for (int i = 0; i < SIZE; i++) {
            if (map[verticalCoordinate][i] == playerSymbol) {
                winPoint += 1;
                if (winPoint == amountSymbolsForWin) {
                    return true;
                }
            } else {
                winPoint = 0;
            }
        }
        return false;
    }

    /**
     * Метод поиска символов игрока по столбцу.
     */
    public static boolean checkColumn(char playerSymbol) {
        int winPoint = 0;

        for (int i = 0; i < SIZE; i++) {
            if (map[i][horizontalCoordinate] == playerSymbol) {
                winPoint += 1;
                if (winPoint == amountSymbolsForWin) {
                    return true;
                }
            } else {
                winPoint = 0;
            }
        }
        return false;
    }

    /**
     * Метод поиска символов игрока по левой диагонали.
     */
    public static boolean checkLeftDiagonal(char playerSymbol) {
        int winPointDown = 0;
        int winPointUp = 0;

/*        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (verticalCoordinate + 1 >= SIZE || horizontalCoordinate + 1 >= SIZE || map[verticalCoordinate + 1][horizontalCoordinate + 1] != playerSymbol) {
                    break;
                } else {
                    winPointUp++;
                    verticalCoordinate++;
                    horizontalCoordinate++;
                    if (winPointUp == amountSymbolsForWin - 1) {
                        return true;
                    }
                }

            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (verticalCoordinate - 1 < 0 || horizontalCoordinate - 1 < 0 || map[verticalCoordinate - 1][horizontalCoordinate - 1] != playerSymbol) {
                    break;
                } else {
                    winPointDown++;
                    verticalCoordinate--;
                    horizontalCoordinate--;
                    if (winPointDown == amountSymbolsForWin - 1) {
                        return true;
                    }

                }
            }

        }*/

        while (true) {
            if (verticalCoordinate + 1 >= SIZE || horizontalCoordinate + 1 >= SIZE || map[verticalCoordinate + 1][horizontalCoordinate + 1] != playerSymbol) {
                break;
            } else {
                winPointUp++;
                verticalCoordinate++;
                horizontalCoordinate++;
            }

            if (winPointUp >= amountSymbolsForWin - 1) {
                return true;
            }
        }

        while (true) {

            if (verticalCoordinate - 1 < 0 || horizontalCoordinate - 1 < 0 || map[verticalCoordinate - 1][horizontalCoordinate - 1] != playerSymbol) {
                break;
            } else {
                winPointDown++;
                verticalCoordinate--;
                horizontalCoordinate--;

            }
            if (winPointDown >= amountSymbolsForWin - 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Метод поиска символов игрока по правой диагонали.
     */
    public static boolean checkRightDiagonal(char playerSymbol) {
        int winPointDown = 0;
        int winPointUp = 0;

        while (true) {
            if (verticalCoordinate + 1 >= SIZE || horizontalCoordinate - 1 < 0 || map[verticalCoordinate + 1][horizontalCoordinate - 1] != playerSymbol) {
                break;
            } else {
                winPointDown++;
                verticalCoordinate++;
                horizontalCoordinate--;
            }

            if (winPointDown == amountSymbolsForWin - 1) {
                return true;
            }
        }

        while (true) {

            if (verticalCoordinate - 1 < 0 || horizontalCoordinate + 1 >= SIZE || map[verticalCoordinate - 1][horizontalCoordinate + 1] != playerSymbol) {
                break;
            } else {
                winPointUp++;
                verticalCoordinate--;
                horizontalCoordinate++;
            }

            if (winPointUp == amountSymbolsForWin - 1) {
                return true;
            }

        }
        return false;
    }

    /**
     * Ход компьютера
     */
    public static void computerTurn() {
        if (!smartComputerTurn()) {
            sillyComputerTurn();
        }
    }

    /**
     * Умный ход компьютера
     */
    public static boolean smartComputerTurn() {


        boolean moveFound = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {

                    verticalCoordinate = i;
                    horizontalCoordinate = j;
                    if (j + 1 < SIZE && map[i][j + 1] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    } else if (j - 1 >= 0 && map[i][j - 1] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    } else if (i - 1 >= 0 && map[i - 1][j] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    } else if (i + 1 < SIZE && map[i + 1][j] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    } else if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    } else if (i + 1 < SIZE && j + 1 < SIZE && map[i + 1][j + 1] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    } else if (i - 1 >= 0 && j + 1 < SIZE && map[i - 1][j + 1] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    } else if (i + 1 < SIZE && j - 1 >= 0 && map[i + 1][j - 1] == DOT_O) {
                        moveFound = true;
                        System.out.println("Умный выбрал ячейку: " + (i + 1) + " " + (j + 1));
                        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
                        break;
                    }
                }
            }
            if (moveFound) {
                break;
            }
        }
        return moveFound;
    }

    /**
     * Рандомный ход компьютера
     */
    private static void sillyComputerTurn() {

        do {
            horizontalCoordinate = random.nextInt(SIZE);
            verticalCoordinate = random.nextInt(SIZE);
        } while (!isCellValid(horizontalCoordinate, verticalCoordinate));
        System.out.println("Рандом выбрал ячейку: " + (verticalCoordinate + 1) + " " + (horizontalCoordinate + 1));
        map[verticalCoordinate][horizontalCoordinate] = DOT_O;
    }

}
