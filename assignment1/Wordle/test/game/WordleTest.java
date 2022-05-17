package game;

import game.Wordle.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleTest {

    Wordle wordle;

    @BeforeEach
    public void setup() {
        wordle = new Wordle();
    }  

    @Test
    public void isGuessRightLengthSubmittable() {
        wordle.setGuess("FAVOR");
        assertEquals(true, wordle.isSubmittable());
    }

    @Test
    public void isGuessWrongLengthSubmittable() {
        wordle.setGuess("FAV");
        assertEquals(false, wordle.isSubmittable());
    }

    @Test
    public void GuessIsEmptyAtStart() {
        assertEquals("", wordle.getGuess());
    }
    
    @Test
    public void tallyForGuessFAVOR() {
        wordle.setGuess("FAVOR");
        assertEquals(List.of(Match.EXACT, Match.EXACT, Match.EXACT, Match.EXACT, Match.EXACT), wordle.tally());
    }

    @Test
    public void tallyForGuessTESTS() {
        wordle.setGuess("TESTS");
        assertEquals(List.of(Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH), wordle.tally());
    }

    @Test
    public void tallyForGuessRAPID() {
        wordle.setGuess("RAPID");
        assertEquals(List.of(Match.WRONG_POS, Match.EXACT, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH), wordle.tally());
    }

    @Test 
    public void tallyForGuessMAYOR() {
        wordle.setGuess("MAYOR");
        assertEquals(List.of(Match.NO_MATCH, Match.EXACT, Match.NO_MATCH, Match.EXACT, Match.EXACT), wordle.tally());
    }

    @Test
    public void tallyForGuessSORRY() {
        wordle.setGuess("SORRY");
        assertEquals(List.of(Match.NO_MATCH, Match.WRONG_POS, Match.WRONG_POS, Match.NO_MATCH, Match.NO_MATCH), wordle.tally());
    }

    @Test
    public void tallyForGuessConsecLettersIn() {
        wordle.setGuess("GRRRR");
        assertEquals(List.of(Match.NO_MATCH, Match.WRONG_POS, Match.NO_MATCH, Match.NO_MATCH, Match.EXACT), wordle.tally());
    }

    @Test
    public void submitGuessForLongerWord() { // Guess word gets truncated during tally
        wordle.setGuess("FLAVOR");
        assertEquals(false, wordle.isSubmittable());
    }

    @Test
    public void tallyGuessForShorterWord() {
        wordle.setGuess("FOR");
        assertEquals(List.of(Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH), wordle.tally());
    }

    @Test 
    public void tallyGuessForNumbers() {
        wordle.setGuess("12345");
        assertEquals(List.of(Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH), wordle.tally());
    }

    @Test 
    public void tallyGuessForLowerCase() {
        wordle.setGuess("favor");
        assertEquals(List.of(Match.EXACT, Match.EXACT, Match.EXACT, Match.EXACT, Match.EXACT), wordle.tally());
    }

    @Test
    public void tallyTargetAndGuessWithRepeatingLetters() {
        wordle.setTarget("LEECH");
        wordle.setGuess("TEETH");

        assertEquals(List.of(Match.NO_MATCH, Match.EXACT, Match.EXACT, Match.NO_MATCH, Match.EXACT), wordle.tally());
    }

    @Test 
    public void tallyTargetWorstCaseGuess() {
        wordle.setTarget("AHDDL");
        wordle.setGuess("AXDXD");
        assertEquals(List.of(Match.EXACT, Match.NO_MATCH, Match.EXACT, Match.NO_MATCH, Match.WRONG_POS), wordle.tally());
    }

    @Test 
    public void tallyTargetWithSecondGuess() {
        //Target word should be FAVOR
        wordle.setGuess("THINK");
        wordle.tally();
        wordle.setGuess("FavOR");
        assertEquals(List.of(Match.EXACT, Match.EXACT, Match.EXACT, Match.EXACT, Match.EXACT), wordle.tally());
    }

    @Test 
    public void tallyTargetWithSecondWrongGuess() {
        //Target word should be FAVOR
        wordle.setGuess("THINK");
        wordle.setGuess("FAMLY");
        assertEquals(List.of(Match.EXACT, Match.EXACT, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH), wordle.tally());
    }

    @Test
    public void resetTallyAfterGuess() {
        wordle.setGuess("FAVOR");
        wordle.tally();
        wordle.resetTally();
        wordle.setGuess("");
        assertEquals(List.of(Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH, Match.NO_MATCH), wordle.tally());
    }
}
