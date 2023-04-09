package ir.sharif.math.ap2023.supermario;

import ir.sharif.math.ap2023.supermario.controllers.GameLoop;
import ir.sharif.math.ap2023.supermario.controllers.PersistenceController;
import ir.sharif.math.ap2023.supermario.views.StartMenu;

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