package ir.sharif.math.ap2023.supermario.controllers;

import ir.sharif.math.ap2023.supermario.models.KeyboardState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyboardState.pressedKeys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyboardState.pressedKeys.put(e.getKeyCode(), false);
    }
}
