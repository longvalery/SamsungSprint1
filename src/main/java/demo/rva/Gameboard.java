package demo.rva;

// import org.fusesource.jansi.AnsiConsole;
import java.util.Scanner;

public class Gameboard {
    // Simple propetries
    private int row;
    private int column;
    private int level;
    private int lives;
    private int currentColumn;
    private int currentRow;
    private int score;
    // answer
    private String answer_text;
    private int answer_result;
    private boolean answer;
    // Complex propetries
    private CellType[][] board;
    private Scanner in = new Scanner(System.in);
    // public methods
    public Gameboard(int row, int column, int lives) {
        this.row = row;
        this.column = column;
        this.lives = lives;
        this.score = 0;
        this.board = new CellType[row][column];
        this.initialGame();
//        AnsiConsole.systemInstall();

        this.answer = true;
        this.answer_result = 0;
        this.answer_text = "";
    }

    public boolean start() {
        System.out.println();
        System.out.println("Начинаем играть ( ДА / НЕТ ) ?");
        String answer = this.in.next().toUpperCase();
        if (answer.equals("ДА")) {
            return true;
        } else {
            System.out.println(answer);
            System.out.println("Почему ты не захотел со мной играть(");
            System.out.println("Приходи ещё!");
            return false;
        }

    }

    public void show() {
        String symbol;
        String line = "+";
        this.clearScreen();
        for (int j = 0; j < this.column; j++) {  line = line + "--+";  }
        System.out.println(line);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                symbol = "  ";
                switch (this.board[i][j]) {
                    case CellType.CASTLE:
                        symbol = "\uD83C\uDFF0"; // "З ";
                        break;
                    case CellType.BIG_MONSTER:
                        symbol = Character.toString(0x1F47B); //"Мб";
                        break;
                    case CellType.LITTLE_MONSTER:
                        symbol = Character.toString(0x1F47D); // "Мм";
                        break;
                    case CellType.PLAYER:
                        symbol =  Character.toString(0x1F471);  // "Гг";
                        break;
                    default:
                        symbol = "  ";
                }
                System.out.print("|" + symbol);
            }
            System.out.println("|");
            System.out.println(line);
        }
        System.out.println(String.format("Жизни  : %d   Уровень: %d  Заработано %d очков", this.lives, this.level, this.score));
        if (! this.answer) {
            System.out.println("Правильный ответ для вопроса");
            System.out.println("'" + this.answer_text + "'");
            System.out.println(String.format("Это - %d", this.answer_result));
        }

    }

    public int inputLevel() {
        int level;
        while (true) {
            System.out.print("Введите уровень от 1 до 3: ");
            if (this.in.hasNextInt()) {
                level = this.in.nextInt();
                if (level >= 1 && level <= 3) {
                    break;
                } else {
                    System.out.println("Ошибка: число должно быть от 1 до 3. Повторите ввод.");
                }
            } else {
                System.out.println("Ошибка: введите целое число.");
                this.in.next(); // очищаем некорректный ввод
            }
        }
        // Scanner не закрываем, чтобы не закрыть System.in
        return level;
    }

    public void loop() {
        int column;
        int row;
        int max;
        int min;
        while (true) {
            this.show();
            System.out.println(String.format("Ваши текущие положение: строка %d, колонка %d",
                    this.currentRow, this.currentColumn));
            System.out.println("Для следующего шага введите:");
            min = this.currentRow - 1;
            if (min < 0) {min = 0;}
            max = this.currentRow + 1;
            if (max >= this.row) {max = this.row - 1;}
            row = inputValue("Строку", min, max);
            if (row == this.currentRow) { // Нельзя ходить по диагонали
                min = this.currentColumn - 1;
                if (min < 0) {
                    min = 0;
                }
                max = this.currentColumn + 1;
                if (max >= this.row) {
                    max = this.column - 1;
                }
                column = inputValue("Колонку", min, max);
            }
            else { column = this.currentColumn; }
            this.makeStep(row, column);
        }


    }

    public void help() {
        this.clearScreen();
        System.out.println("Игра 'Волшебное путешествие'");
        System.out.println("  Вам (" + Character.toString(0x1F471)
                + ") необходимо добраться до замка (" + "\uD83C\uDFF0"
                +"), преодолевая испытания разных монстров.");
        System.out.println("  " + Character.toString(0x1F47B)
                + "(монстр большой) и " +  Character.toString(0x1F47D)
                + "(монстр маленький) - разные виды монстров.");
        System.out.println("  Каждый вид монстра будет задавать разные задания.");
        System.out.println("  Если Вы неправильно решаете задачу монстра, то снимается одна жизнь. ");
        System.out.println(String.format("  Жизней всего %d.", this.lives));
        System.out.println("  Вы можете двигаться только по горизонтали/вертикали на одну клетку. Ходить по диагонали запрещено.");
        System.out.println("  Ход осуществляется вводом координат СТРОКА, КОЛОНКА.");
        System.out.println(String.format("  Номер СТРОКИ  от 0 до %d ", this.row - 1));
        System.out.println(String.format("  Номер КОЛОНКИ от 0 до %d ", this.column - 1));

    }

    // private methods
    private void initialGame() {
        // Clear all board
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                this.board[i][j] = CellType.EMPTY;
            }
        }
        // Set Hero
        int randomInt = (int) (Math.random() * (this.column - 1));
        this.board[this.row - 1][randomInt] = CellType.PLAYER;
        this.currentRow = this.row - 1;
        this.currentColumn = randomInt;
        // set Castle
        randomInt = (int) (Math.random() * (this.column - 1));
        int randomInt100;
        this.board[0][randomInt] = CellType.CASTLE;
        // set Monsters
        for (int i = 0; i < this.row - 1; i++) {
            for (int j = 0; j < (this.column); j++) {
                randomInt = (int) (Math.random() * (this.column - 1));
                if (this.board[i][randomInt] == CellType.EMPTY) {
                    randomInt100 = (int) (Math.random() * ((100 - 1) + 1)) + 1;
                    if (randomInt100 < 50) { this.board[i][randomInt] = CellType.BIG_MONSTER; }
                    else                   { this.board[i][randomInt] = CellType.LITTLE_MONSTER; }
                }
            }
        }
    }

    private void clearScreen() {
        for (int i=0; i<80; i++) { System.out.println(); }
    }

    private int inputValue(String text, int min, int max) {
        int value;
        while (true) {
            System.out.print(String.format("Введите %s от %d до %d: ", text, min, max));
            if (this.in.hasNextInt()) {
                value = this.in.nextInt();
                if ((value >= min) && (value <= max)) {
                    break;
                } else {
                    System.out.println(String.format("Ошибка: число должно быть от %d до %d. Повторите ввод.", min, max));
                }
            } else {
                System.out.println("Ошибка: введите целое число.");
                this.in.next(); // очищаем некорректный ввод
            }
        }
        // Scanner не закрываем, чтобы не закрыть System.in
        return value;
    }

    private void makeStep(int row, int column){
        this.answer = true;
        this.board[this.currentRow][currentColumn] = CellType.EMPTY;
        this.currentRow = row;
        this.currentColumn = column;
        this.check();
        this.board[this.currentRow][currentColumn] = CellType.PLAYER;
    }

    private void lose() {
        this.clearScreen();
        System.out.println("Увы, Вы проиграли... ");
        System.out.println("Попробуем еще раз? Надо запустить игру снова.");
        System.exit(1);
    }

    private void win() {
        this.clearScreen();
        System.out.println("Отлично! Вы дошли!!!");
        System.out.println(String.format("Заработано %d очков", this.score));
        System.out.println("Поздравляю с победой!!!");
        System.exit(0);
    }

    private void check() {
        if (this.lives == 0) { this.lose(); }
        if (this.board[this.currentRow][this.currentColumn] == CellType.CASTLE) { this.win(); }
        if (this.board[this.currentRow][this.currentColumn] == CellType.LITTLE_MONSTER) {
            if (! this.talkLittleMonster()) { this.lives = this.lives - 1; }
            else                            { this.score += 1;}
        }
        if (this.board[this.currentRow][this.currentColumn] == CellType.BIG_MONSTER) {
           if (! this.talkBigMonster()) { this.lives = this.lives - 1; }
           else                         { this.score += 10;}
                                                                                      }
    }

    private boolean talkLittleMonster(){
        Monster monster = new Monster(this.level);
        this.clearScreen();
        System.out.println("Опаньки! На пути монстр.");
        this.answer = monster.getAnswer();
        this.answer_result = monster.getResult();
        this.answer_text = monster.getMessage();
        if (! this.answer) { this.lives = this.lives - 1; }
        return this.answer;
    }

    private boolean talkBigMonster(){
        BigMonster monster = new BigMonster(this.level);
        this.clearScreen();
        System.out.println("Обана! Влипли! Большой монстр.");
        this.answer = monster.getAnswer();
        this.answer_result = monster.getResult();
        this.answer_text = monster.getMessage();
        return this.answer;
    }

    // setters andd getters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public CellType[][] getBoard() {
        return board;
    }
}