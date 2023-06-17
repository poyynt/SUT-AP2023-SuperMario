package supermario;

import supermario.controllers.GameLoop;
import supermario.controllers.PersistenceController;
import supermario.views.StartMenu;

public class Main {
    public static void main(String[] args) {
        PersistenceController.load();

        StartMenu startMenu = StartMenu.getInstance();
        GameLoop gameLoop = GameLoop.getInstance();

        gameLoop.start();
        startMenu.show();
    }

    public static void exit(int status) {
        PersistenceController.save();
        System.exit(status);
    }
}