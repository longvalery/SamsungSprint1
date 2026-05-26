package demo.rva;

import sun.misc.Signal;


public class Main {
    public static void main(String[] args) {
        // Регистрируем обработчик для сигнала SIGINT (Ctrl+C)
        Signal.handle(new Signal("INT"), signal -> {
            System.out.println("\nЖалко... но, может быть в следующий раз...");
            System.exit(20);   // Завершаем JVM с кодом 20
        });
        Gameboard game = new Gameboard(5,5, 3);
        game.help();
        if (game.start()) {
            int level = game.inputLevel();
            game.setLevel(level);
            game.loop();
                          }
        }
}