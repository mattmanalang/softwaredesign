# The Wordle Clone

The end product of this assignment was to be a piece of software simulating the puzzle-game Wordle.<br>
The core logic of the game is present, but the UI in this version does nothing significant.<br>
Here are the rules of this version of the game that were given to the students:
1. The game will only allow us to play against the word "FAVOR". However, we will design it to be extended for other words to guess.
2. When the game starts, the player is presented with the guessing grid (6 rows of 5 boxes). In other words, the player has 6 attempts to guess a 5-letter word.
3. A "Guess" button is initially disable, but will become active once a 5-letter word is entered. When the player removes a letter, the button is made inactive again
4. When a user types a letter, it appears in upper case within a box in the currently active row. Any keystrokes that aren't letters are ignored.
5. When a user clicks on the "Guess" button, the currently active row becomes inactive and any further input will not affect that row. The next row becomes active unless the player has filled all six rows or has won the game.
6. Once the "Guess" button is clicked, each box in the active row changes colors to either green, yellow, or gray.
7. Once the player wins, they cannot enter any more guesses and will have to quit the game.
8. Winning on the first try results in displaying "Amazing". Winning on the second try results in a "Splendid". Winning on the third try results in an "Awesome". Winning on the fourth or greater try results in a "Yay".
9. Failure to guess the correct word after six tries displays "It was [the word], better luck next time."
