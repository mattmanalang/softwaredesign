package game.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.Wordle;

import java.awt.*;

public class WordleFrame extends JFrame{
    private static final int SIZE = 5;
    static Wordle wordle;
    
    @Override
    protected void frameInit() {
        super.frameInit();
        setLayout(new GridLayout(5,6));
        wordle = new Wordle();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                WordleLetterCell cell = new WordleLetterCell(i, j);
                getContentPane().add(cell);                
            }
            
        }
    }

    public static void main(String[] args) {
        JFrame frame = new WordleFrame();
        frame.setSize(700, 700);
        frame.setVisible(true);

        String playerGuess = JOptionPane.showInputDialog("Make a guess!");
        wordle.setGuess(playerGuess);
        
        
    }

    //Maybe use JOptionPane to guess a word and use that info to display onto the game board?
}
