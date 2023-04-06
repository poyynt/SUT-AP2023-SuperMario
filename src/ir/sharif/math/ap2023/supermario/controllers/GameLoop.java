package ir.sharif.math.ap2023.supermario.controllers;

import ir.sharif.math.ap2023.supermario.views.MainView;

public class GameLoop extends Loop {
    private static final GameLoop gameLoop = new GameLoop();
    private final KeyboardHandler keyboardHandler = new KeyboardHandler();

    private GameLoop() {
        super(30);
    }

    public static GameLoop getInstance() {
        return gameLoop;
    }

    public void update() {
        MainView.getInstance().update();
    }

    @Override
    public void start() {
        MainView mainView = MainView.getInstance();
        mainView.getFrame().setFocusable(true);
        mainView.getFrame().addKeyListener(keyboardHandler);
//        mainView.setupFrame();
        super.start();
    }
}
