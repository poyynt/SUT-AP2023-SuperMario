package supermario;

import supermario.controllers.AudioController;
import supermario.controllers.GameLoop;
import supermario.controllers.PersistenceController;
import supermario.views.StartMenu;

import javax.sound.sampled.Clip;

public class Main {
    public static void main(String[] args) {
        AudioController.playWavAudioOnChannel("background", "Title", Clip.LOOP_CONTINUOUSLY);

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