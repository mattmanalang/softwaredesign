package game.ui;
import javax.swing.*;

import game.Wordle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class WordleUI{
    public static JFrame mainWindow;
    public Wordle.GameState gameStatus = Wordle.GameState.IN_PROGRESS;
    private WordleUIInputHandler listener = new WordleUIInputHandler();
    private int mainWindowWidth = 350;
    private int mainWindowHeight = 500;
    private int attemptNumber = 0;
    private static final int GUESSES_TOTAL = 6;
    private static final int WORD_SIZE = 5;
    private static final Color colorDefaultLetter = new Color (255,255,255);
    private static final Color colorNoneLetter = new Color(100,100,100);
    private static final Color colorMatchLetter = new Color(230,190,50);
    private static final Color colorExactLetter = new Color(100,230, 100);
    private static String activeGuessText;
    private static String targetWord = getTargetWord();

    public static String getTargetWord() {
        try {
            return Wordle.chooseRandomTargetWord();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error getting word from online word bank");
            return "";
        }
    }

    public void createFrame(){
        mainWindow = new JFrame("Wordle");
        mainWindow.setSize(mainWindowWidth, mainWindowHeight);

        Container mainWindowPane = mainWindow.getContentPane();
        mainWindowPane.setLayout(new GridLayout(GUESSES_TOTAL + 1,1));

        createGuesses();
        createSubmitButtonAndGuessField();

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.setFocusable(true);

        mainWindow.addKeyListener(listener);
        mainWindow.setVisible(true);
    }


    private void createSubmitButtonAndGuessField() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));

        JButton submitButton = new JButton("Submit Guess");

        JTextField guessInput = new JTextField();
        guessInput.setHorizontalAlignment(0);
        WordleUI frame = this;

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                Supplier<String> guess = () -> guessInput.getText().toUpperCase();
                activeGuessText = guessInput.getText().toUpperCase();
                // Wordle.play(targetWord, guess, frame, attemptNumber); // Broken with new play() implementation...
                guessInput.setText("");
                attemptNumber += 1;
            }
        });


        panel.add(submitButton);
        panel.add(guessInput);
        mainWindow.add(panel);
    }

    private JLabel defaultGuessLabelProperties(JLabel label, String text){
        label.setText(text);
        label.setHorizontalAlignment(0);
        label.setBorder(BorderFactory.createBevelBorder(0));
        label.setOpaque(true);
        label.setBackground(colorDefaultLetter);
        label.setFont(new Font("Sans Serif", Font.PLAIN, 50 ));

        return label;
    }

    private void createGuesses() {
        JPanel guess;
        JLabel guessLabel;
        for (int i = 0; i < GUESSES_TOTAL; i++){
            guess = new JPanel();
            guess.setBorder(BorderFactory.createBevelBorder(1));
            guess.setLayout(new GridLayout(1, WORD_SIZE));
            for (int j = 0; j < WORD_SIZE; j++){
                guessLabel = new JLabel();
                guessLabel = defaultGuessLabelProperties(guessLabel,"");
                guess.add(guessLabel);
            }
            mainWindow.add(guess);
        }
    }

    public static void resetRowForGraceTurn(String notAWordMsg) {
        JOptionPane.showMessageDialog(mainWindow, notAWordMsg);
    }

    public static void displayErrorMessage(String errMsg) {
        JOptionPane.showMessageDialog(mainWindow, errMsg);
    }

    public static List<JLabel> processLabels(List<Wordle.Match> score, JPanel guessPanel){
        return IntStream.range(0, WORD_SIZE)
                .mapToObj(index -> labelForPosition(score, guessPanel, index))
                .collect(toList());
    }

    private static JLabel labelForPosition(List<Wordle.Match> score, JPanel guessPanel, int position){
        JLabel guessLetter = (JLabel)guessPanel.getComponents()[position];
        if (activeGuessText == null)
            guessLetter.setText("?");
        else
            guessLetter.setText("" + activeGuessText.charAt(position));

        if (score.get(position) == Wordle.Match.EXACT)
            guessLetter.setBackground(colorExactLetter);
        else if (score.get(position) == Wordle.Match.MATCH)
            guessLetter.setBackground(colorMatchLetter);
        else
            guessLetter.setBackground(colorNoneLetter);

        return guessLetter;
    }

    public void processGuessDisplay(int attemptNum, List<Wordle.Match> score){
        Container mainWindowPane = mainWindow.getContentPane();
        JPanel guessDisplay = (JPanel)mainWindowPane.getComponents()[attemptNum];
        List<JLabel> guess = processLabels(score, guessDisplay);
    }

    public void checkForGameReset(Wordle.GameState status) {
        gameStatus = status;
        if (status == Wordle.GameState.IN_PROGRESS)
            return;
        if (status == Wordle.GameState.WIN)
            displayWinDialog();
        else if (status == Wordle.GameState.LOST)
            displayLossDialog();

        resetDisplay();
    }

    private void resetDisplay() {
        Container mainWindowPane = mainWindow.getContentPane();
        JPanel guessDisplay;
        JLabel[] guess = new JLabel[WORD_SIZE];
        for (int i = 0; i < GUESSES_TOTAL; i++){
            guessDisplay = (JPanel)mainWindowPane.getComponents()[i];
            for (int j = 0; j < WORD_SIZE; j++){
                guess[j] = (JLabel)guessDisplay.getComponents()[j];
                guess[j] = defaultGuessLabelProperties(guess[j], "");
            }
        }
        targetWord = "FAVOR";
        attemptNumber = -1;
    }

    private void displayLossDialog(){
        JOptionPane.showMessageDialog(mainWindow, "You lost! The word was " + targetWord);
    }

    private void displayWinDialog(){
        JOptionPane.showMessageDialog(mainWindow, "You won!");
    }

    public static void main(String[] args) {
        WordleUI wordleUI = new WordleUI();
        wordleUI.createFrame();
    }
}
