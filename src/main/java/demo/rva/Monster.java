package demo.rva;

import java.util.Scanner;

public class Monster {
    private int x;
    private int y;
    private int result;
    private String message;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Monster(int level) {
        int randomInt = (int) (Math.random() * ((10 * level - 1) + 1)) + 1;
        this.x = randomInt;
        randomInt = (int) (Math.random() * ((10 * level - 1) + 1)) + 1;
        this.y = randomInt;
        this.result = this.x * this.y;
        this.message = String.format("Реши пример: %d * %d = ?", this.x, this.y);
    }

    public boolean getAnswer() {
        System.out.println(this.message);
        int answer = this.inputAnswer();
        return answer == this.result;
    }

    private int inputAnswer() {
        int value;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Введите ответ (число):");
            if (in.hasNextInt()) {
                value = in.nextInt();
                break;
                                  }
            else {
                System.out.println("Ошибка: введите целое число.");
                in.next(); // очищаем некорректный ввод
            }
        }
        // Scanner не закрываем, чтобы не закрыть System.in
        return value;
    }
}
