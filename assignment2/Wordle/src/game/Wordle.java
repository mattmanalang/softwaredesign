package game;

import java.util.List;
import java.util.stream.*;
import java.util.function.*;
import static java.util.stream.Collectors.*;
import static game.SpellCheckerService.checkSpelling;

import java.io.IOException;

public interface Wordle {
  enum Match { NONE, MATCH, EXACT };
  enum GameState { WIN, IN_PROGRESS, LOST}
  final int WORD_SIZE = 5;
  final int MAX_GUESSES = 6;

  static String chooseRandomTargetWord() throws IOException {
    List<String> words = GetWordService.getWordSet();
    return words.get((int) (Math.random() * words.size()));
  }
  
  private static long countPositionalMatches(String target, String guess, char letter) {
    return IntStream.range(0, WORD_SIZE)
      .filter(index -> target.charAt(index) == letter)
      .filter(index -> target.charAt(index) == guess.charAt(index))
      .count();
  }
  
  private static long countNumberOfOccurrencesUntilPosition(String word, char letter, int position) {
    return IntStream.rangeClosed(0, position)
      .filter(index -> word.charAt(index) == letter)
      .count();
  }

  private static Match tallyForPosition(String target, String guess, int position) {
    if(target.charAt(position) == guess.charAt(position)) {
      return Match.EXACT;
    }
    
    char theLetter = guess.charAt(position);
    
    var numberOfPositionalMatches = countPositionalMatches (target, guess, theLetter);
    var numberOfNonPositionalOccurrencesInTarget =
      countNumberOfOccurrencesUntilPosition(target, theLetter, WORD_SIZE - 1) - numberOfPositionalMatches;
    var numberOfOccurancesInGuessUntilPosition =
      countNumberOfOccurrencesUntilPosition(guess, theLetter, position);
    
    if(numberOfNonPositionalOccurrencesInTarget >= numberOfOccurancesInGuessUntilPosition) {
      return Match.MATCH;
    }
        
    return Match.NONE;
  }

  private static void verifyCorrectLength(String target, String guess) {
    if (target.length() != guess.length()) {
      throw new RuntimeException("invalid guess: incorrect length");
    }
  }

  private static void verifyValidCharacters(String target, String guess) {
    if (!guess.matches("^[a-zA-Z]*$")){
      throw new RuntimeException("invalid guess: forbidden characters");
    }
  }

  public static boolean verifySpelling(String guess) throws IOException {
    return SpellCheckerService.checkSpelling(guess);
  }

  public static List<Match> tally(String target, String guess) {
    verifyCorrectLength(target, guess); 
    verifyValidCharacters(target, guess);

    return IntStream.range(0, WORD_SIZE)
      .mapToObj(index -> tallyForPosition(target, guess, index))
      .collect(toList());
  }

  private static GameState determineGameState(int numberOfAttempts, List<Match> matches) {
    if(matches.stream().allMatch(match -> match == Match.EXACT)) {
      return GameState.WIN;
    }
    if(numberOfAttempts == MAX_GUESSES) {
      return GameState.LOST;
    }
    return GameState.IN_PROGRESS;
  }
  
  private static String createMessage(int numberOfAttempts, GameState gameState, String target) {
    if(gameState == GameState.WIN) {
      switch(numberOfAttempts) {
        case 1: return "Amazing";
        case 2: return "Splendid";
        case 3: return "Awesome";
        default: return "Yay";
      }
    }
    else if (gameState == GameState.LOST) {
      return String.format("It was %s, better luck next time.", target);
    }
    
    return "";
  }

  public static void play(String target, Supplier<String> readGuess, Display display) {
    for(int attempts = 1; attempts <= MAX_GUESSES; attempts++) {
      var guess = readGuess.get();
      try {
        if (!checkSpelling(guess)) {
          attempts--;
          continue;
        }
      } catch (IOException ex) {
        
      }
      
      var matches = tally(target, guess);
      var gameState = determineGameState(attempts, matches);
      var message = createMessage(attempts, gameState, target);
    
      display.call(attempts, gameState, matches, message);
      
      if(gameState == GameState.WIN) {
        break;
      }
    }
  }
}


