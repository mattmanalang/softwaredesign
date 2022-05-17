package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;
import static game.Wordle.Match.*;
import game.Wordle.Match;
import game.Wordle.GameState;
import static game.Wordle.GameState.*;
import java.util.function.*;
import java.util.concurrent.atomic.*;

public class WordleTest {
    @Test
    void canary() {
      assertTrue(true);
    }

    @Test
    void testForTally() {
      assertAll(
        () -> assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, EXACT), Wordle.tally("FAVOR", "FAVOR")),
        () -> assertEquals(List.of(NONE, NONE, NONE, NONE, NONE), Wordle.tally("FAVOR", "TESTS")),
        () -> assertEquals(List.of(MATCH, EXACT, NONE, NONE, NONE), Wordle.tally("FAVOR", "RAPID")),
        () -> assertEquals(List.of(NONE, EXACT, NONE, EXACT, EXACT), Wordle.tally("FAVOR", "MAYOR")),
        () -> assertEquals(List.of(NONE, NONE, EXACT, NONE, EXACT), Wordle.tally("FAVOR", "RIVER")),
        () -> assertEquals(List.of(MATCH, NONE, NONE, NONE, NONE), Wordle.tally("FAVOR", "AMAST")),
        () -> assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, EXACT), Wordle.tally("SKILL", "SKILL")),
        () -> assertEquals(List.of(EXACT, NONE, EXACT, NONE, EXACT), Wordle.tally("SKILL", "SWIRL")),
        () -> assertEquals(List.of(NONE, MATCH, NONE, NONE, EXACT), Wordle.tally("SKILL", "CIVIL")),
        () -> assertEquals(List.of(EXACT, NONE, EXACT, NONE, NONE), Wordle.tally("SKILL", "SHIMS")),
        () -> assertEquals(List.of(EXACT, MATCH, MATCH, EXACT, NONE), Wordle.tally("SKILL", "SILLY")),
        () -> assertEquals(List.of(EXACT, MATCH, EXACT, NONE, NONE), Wordle.tally("SKILL", "SLICE")),
        () -> assertEquals(List.of(MATCH, NONE, MATCH, MATCH, NONE), Wordle.tally("SAGAS", "ABASE"))
      );
    }

    @Test
    void testForIncorrectWordLength() {
      assertAll(
        () -> { 
          var ex = assertThrows(RuntimeException.class, () -> Wordle.tally("FAVOR", "FOR"));
          assertEquals("invalid guess: incorrect length", ex.getMessage());
        },
        () -> {
          var ex = assertThrows(RuntimeException.class, () -> Wordle.tally("FAVOR", "FERVER"));
          assertEquals("invalid guess: incorrect length", ex.getMessage());
        }
      );
    }

    @Test
    void testForInvalidCharacters() {
      assertAll(
        () -> {
          var ex = assertThrows(RuntimeException.class, () -> Wordle.tally("FAVOR", "12345"));
          assertEquals("invalid guess: forbidden characters", ex.getMessage());
        },
        () -> {
          var ex = assertThrows(RuntimeException.class, () -> Wordle.tally("FAVOR", "F@V0R"));
          assertEquals("invalid guess: forbidden characters", ex.getMessage());
        }
      );
    }

    @Test
    void testForSpelling() {
      assertAll(
        () -> assertTrue(Wordle.verifySpelling("favor")),
        () -> assertTrue(Wordle.verifySpelling("SUGAR")),
        () -> assertTrue(Wordle.verifySpelling("sHOrt")),
        () -> assertFalse(Wordle.verifySpelling("fvaor"))
      ); 
    }
 
    @Test
    void testPlayWithFirstAttemtWinningWord() {
      Supplier<String> readGuess = () -> "FAVOR";
      AtomicBoolean displayCalled = new AtomicBoolean(false);
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(1, numberOfAttempts);
        assertEquals(WIN, gameState);
        assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, EXACT), matches);
        assertEquals("Amazing", message);
        displayCalled.set(true);
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertTrue(displayCalled.get());
    }

    @Test
    void testPlayWithSecondAttemptWinningWord() {
      var guesses = new LinkedList<String>(List.of("TESTS", "FAVOR"));
      Supplier<String> readGuess = guesses::pop;
      
      AtomicInteger displayCallCount = new AtomicInteger(0);
      var attempts = new LinkedList<Integer>(List.of(1, 2));
      var gameStates = new LinkedList<GameState>(List.of(IN_PROGRESS, WIN));
      var expectedMatches = new LinkedList<List<Match>>(List.of(
        List.of(NONE, NONE, NONE, NONE, NONE),
        List.of(EXACT, EXACT, EXACT, EXACT, EXACT)
      ));
      var messages = new LinkedList<String>(List.of("", "Splendid"));
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(attempts.pop(), numberOfAttempts);
        assertEquals(gameStates.pop(), gameState);
        assertEquals(expectedMatches.pop(), matches);
        assertEquals(messages.pop(), message);
        
        displayCallCount.incrementAndGet();
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertEquals(2, displayCallCount.get());
    }

    @Test
    void testPlayWithThirdAttemptWinningWord() {
      var guesses = new LinkedList<String>(List.of("TESTS", "MAYOR", "FAVOR"));
      Supplier<String> readGuess = guesses::pop;
      
      AtomicInteger displayCallCount = new AtomicInteger(0);
      var attempts = new LinkedList<Integer>(List.of(1, 2, 3));
      var gameStates = new LinkedList<GameState>(List.of(IN_PROGRESS, IN_PROGRESS, WIN));
      var expectedMatches = new LinkedList<List<Match>>(List.of(
        List.of(NONE, NONE, NONE, NONE, NONE),
        List.of(NONE, EXACT, NONE, EXACT, EXACT),
        List.of(EXACT, EXACT, EXACT, EXACT, EXACT)
      ));
      var messages = new LinkedList<String>(List.of("", "", "Awesome"));
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(attempts.pop(), numberOfAttempts);
        assertEquals(gameStates.pop(), gameState);
        assertEquals(expectedMatches.pop(), matches);
        assertEquals(messages.pop(), message);
        
        displayCallCount.incrementAndGet();
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertEquals(3, displayCallCount.get());
    }

    @Test
    void testPlayWithFourthAttemptWinningWord() {
      var guesses = new LinkedList<String>(List.of("TESTS", "MAYOR", "RIVER", "FAVOR"));
      Supplier<String> readGuess = guesses::pop;
      
      AtomicInteger displayCallCount = new AtomicInteger(0);
      var attempts = new LinkedList<Integer>(List.of(1, 2, 3, 4));
      var gameStates = new LinkedList<GameState>(List.of(IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, WIN));
      var expectedMatches = new LinkedList<List<Match>>(List.of(
        List.of(NONE, NONE, NONE, NONE, NONE),
        List.of(NONE, EXACT, NONE, EXACT, EXACT),
        List.of(NONE, NONE, EXACT, NONE, EXACT),
        List.of(EXACT, EXACT, EXACT, EXACT, EXACT)
      ));
      var messages = new LinkedList<String>(List.of("", "", "", "Yay"));
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(attempts.pop(), numberOfAttempts);
        assertEquals(gameStates.pop(), gameState);
        assertEquals(expectedMatches.pop(), matches);
        assertEquals(messages.pop(), message);
        
        displayCallCount.incrementAndGet();
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertEquals(4, displayCallCount.get());
    }

    @Test
    void testPlayWithFifthAttemptWinningWord() {
      var guesses = new LinkedList<String>(List.of("TESTS", "MAYOR", "RIVER", "RAPID", "FAVOR"));
      Supplier<String> readGuess = guesses::pop;
      
      AtomicInteger displayCallCount = new AtomicInteger(0);
      var attempts = new LinkedList<Integer>(List.of(1, 2, 3, 4, 5));
      var gameStates = new LinkedList<GameState>(List.of(IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, WIN));
      var expectedMatches = new LinkedList<List<Match>>(List.of(
        List.of(NONE, NONE, NONE, NONE, NONE),
        List.of(NONE, EXACT, NONE, EXACT, EXACT),
        List.of(NONE, NONE, EXACT, NONE, EXACT),
        List.of(MATCH, EXACT, NONE, NONE, NONE),
        List.of(EXACT, EXACT, EXACT, EXACT, EXACT)
      ));
      var messages = new LinkedList<String>(List.of("", "", "", "", "Yay"));
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(attempts.pop(), numberOfAttempts);
        assertEquals(gameStates.pop(), gameState);
        assertEquals(expectedMatches.pop(), matches);
        assertEquals(messages.pop(), message);
        
        displayCallCount.incrementAndGet();
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertEquals(5, displayCallCount.get());
    }
    
    @Test
    void testPlayWithSixthAttemptWinningWord() {
      var guesses = new LinkedList<String>(List.of("TESTS", "MAYOR", "RIVER", "RAPID", "AMASS", "FAVOR"));
      Supplier<String> readGuess = guesses::pop;
      
      AtomicInteger displayCallCount = new AtomicInteger(0);
      var attempts = new LinkedList<Integer>(List.of(1, 2, 3, 4, 5, 6));
      var gameStates = new LinkedList<GameState>(List.of(IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, WIN));
      var expectedMatches = new LinkedList<List<Match>>(List.of(
        List.of(NONE, NONE, NONE, NONE, NONE),
        List.of(NONE, EXACT, NONE, EXACT, EXACT),
        List.of(NONE, NONE, EXACT, NONE, EXACT),
        List.of(MATCH, EXACT, NONE, NONE, NONE),
        List.of(MATCH, NONE, NONE, NONE, NONE),
        List.of(EXACT, EXACT, EXACT, EXACT, EXACT)
      ));
      var messages = new LinkedList<String>(List.of("", "", "", "", "", "Yay"));
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(attempts.pop(), numberOfAttempts);
        assertEquals(gameStates.pop(), gameState);
        assertEquals(expectedMatches.pop(), matches);
        assertEquals(messages.pop(), message);
        
        displayCallCount.incrementAndGet();
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertEquals(6, displayCallCount.get());
    }

    @Test
    void testPlayWithSixthAttemptLosingWord() {
      var guesses = new LinkedList<String>(List.of("TESTS", "MAYOR", "RIVER", "RAPID", "AMASS", "BOATS"));
      Supplier<String> readGuess = guesses::pop;
      
      AtomicInteger displayCallCount = new AtomicInteger(0);
      var attempts = new LinkedList<Integer>(List.of(1, 2, 3, 4, 5, 6));
      var gameStates = new LinkedList<GameState>(List.of(IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, LOST));
      var expectedMatches = new LinkedList<List<Match>>(List.of(
        List.of(NONE, NONE, NONE, NONE, NONE),
        List.of(NONE, EXACT, NONE, EXACT, EXACT),
        List.of(NONE, NONE, EXACT, NONE, EXACT),
        List.of(MATCH, EXACT, NONE, NONE, NONE),
        List.of(MATCH, NONE, NONE, NONE, NONE),
        List.of(NONE, MATCH, MATCH, NONE, NONE)
      ));
      var messages = new LinkedList<String>(List.of("", "", "", "", "", "It was FAVOR, better luck next time."));
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(attempts.pop(), numberOfAttempts);
        assertEquals(gameStates.pop(), gameState);
        assertEquals(expectedMatches.pop(), matches);
        assertEquals(messages.pop(), message);
        
        displayCallCount.incrementAndGet();
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertEquals(6, displayCallCount.get());
    }

    @Test
    void testGetWordServiceForWords() throws IOException {
      var wordBank = new ArrayList<String>(List.of("FAVOR", "RIGOR", "SUGAR", "POWER", "POINT", "PIOUS", "GRIND", "NASTY", "WATER", "AVOID", "PAINT", "ABBEY", "SHIRE", "CYCLE", "SHORT", "WHICH", "YIELD", "AGILE", "BUILD", "BRICK"));

      assertTrue(wordBank.contains(Wordle.chooseRandomTargetWord()));
    }

    @Test
    void testPlayWithSixthAttemptWrongSpelling() {
      var guesses = new LinkedList<String>(List.of("TESTS", "MAYOR", "RIVER", "RAPID", "AMASS", "FAVPR", "FAVOR"));
      Supplier<String> readGuess = guesses::pop;
      
      AtomicInteger displayCallCount = new AtomicInteger(0);
      var attempts = new LinkedList<Integer>(List.of(1, 2, 3, 4, 5, 6));
      var gameStates = new LinkedList<GameState>(List.of(IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, IN_PROGRESS, WIN));
      var expectedMatches = new LinkedList<List<Match>>(List.of(
        List.of(NONE, NONE, NONE, NONE, NONE),
        List.of(NONE, EXACT, NONE, EXACT, EXACT),
        List.of(NONE, NONE, EXACT, NONE, EXACT),
        List.of(MATCH, EXACT, NONE, NONE, NONE),
        List.of(MATCH, NONE, NONE, NONE, NONE),
        List.of(EXACT, EXACT, EXACT, EXACT, EXACT)
      ));
      var messages = new LinkedList<String>(List.of("", "", "", "", "", "Yay"));
      
      Display display = (numberOfAttempts, gameState, matches, message) -> {
        assertEquals(attempts.pop(), numberOfAttempts);
        assertEquals(gameStates.pop(), gameState);
        assertEquals(expectedMatches.pop(), matches);
        assertEquals(messages.pop(), message);
        
        displayCallCount.incrementAndGet();
      };
      
      Wordle.play("FAVOR", readGuess, display);
      
      assertEquals(6, displayCallCount.get());
    }
}