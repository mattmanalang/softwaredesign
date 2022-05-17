package game.ui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WordleFrameInputHandler implements KeyListener{
    char letter;
    int command;

    @Override
    public void keyTyped(KeyEvent event){
        System.out.println(event.getKeyChar());
        letter = event.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        command = event.getKeyCode();
        System.out.println(event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }
}
