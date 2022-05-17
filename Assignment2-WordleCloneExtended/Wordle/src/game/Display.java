package game;

import java.util.List;

public interface Display {
  void call(int numberOfAttempts, Wordle.GameState gameState, List<Wordle.Match> matches, String message);
}