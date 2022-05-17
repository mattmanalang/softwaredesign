package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Wordle {

  private static Word targetWord;
  private static String targetString;
  private String playerGuess;
  public static final int SIZE = 5;
  public enum Match { EXACT, WRONG_POS, NO_MATCH };
  public List<Match> matches;
  public HashMap<Character, Integer> composition;

  public Wordle() {
    playerGuess = "";
    targetWord = new Word("FAVOR");
    targetString = targetWord.getWord();
    matches = new ArrayList<Match>();
    composition = targetWord.getComposition();
  }

  public boolean isSubmittable() {
    return targetString.length() == playerGuess.length();
  }

  public void setTarget(String newWord) {
    targetWord.setWord(newWord);
    targetString = targetWord.getWord();
  }

  public void setGuess(String oneGuess) {
    playerGuess = oneGuess.toUpperCase();
  }

  public String getGuess() {
    return playerGuess;
  }

  public void resetTally() {
    matches.clear();
    composition = targetWord.getComposition();
  }

  public List<Match> tally() { // Cyclo of 2
    try {
      resetTally();
      for (int i = 0; i < SIZE; i++) { // + 1
        char guessChar = playerGuess.charAt(i);
        char targetChar = targetString.charAt(i);
  
        setMatches(guessChar, targetChar); // + 1
      }
      return matches;
    }
    catch (StringIndexOutOfBoundsException err) {
      return List.of(Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH);
    }
  }

  private void setMatches(char guessChar, char targetChar) { // Cyclo of 4
    if (guessChar == targetChar) { // + 1
      composition.put(guessChar, composition.get(guessChar) - 1);
      matches.add(Match.EXACT); // + 1?
    }
   
    else if ((targetString.indexOf(guessChar) != -1) && (composition.get(guessChar) > 0)) { // + 2
      composition.put(guessChar, (composition.get(guessChar) - 1));
      matches.add(Match.WRONG_POS);
    }
    
    else
      matches.add(Match.NO_MATCH);
  }
  
}

