package game.ui;

import javax.swing.JLabel;

public class WordleLetterCell extends JLabel{
    public final int row;
    public final int col;

    public WordleLetterCell(int numOfRows, int numOfColumns) {
        row = numOfRows;
        col = numOfColumns;
        setSize(100, 100);
    }
    
}
