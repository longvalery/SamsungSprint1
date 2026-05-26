package demo.rva;

public class Main {
    public static void main(String[] args) {
    Gameboard game = new Gameboard(5,5, 3);
    game.help();
    if (game.start()) {
        int level = game.inputLevel();
        game.setLevel(level);
        game.loop();
                      }
    }
}