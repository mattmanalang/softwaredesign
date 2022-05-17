package game;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public interface SpellCheckerService {
    final String spellChkURL = "http://agilec.cs.uh.edu/spell?check=";

    private static Scanner openConnection(String guess) throws IOException {
        URL url = new URL(spellChkURL + guess.toUpperCase());
        Scanner spellChecker = new Scanner(url.openStream());

        return spellChecker;
    }
    
    public static boolean checkSpelling(String guess) throws IOException {
        Scanner spellChecker = openConnection(guess);
        boolean correctSpelling = spellChecker.nextBoolean();
        return correctSpelling;
    }
}