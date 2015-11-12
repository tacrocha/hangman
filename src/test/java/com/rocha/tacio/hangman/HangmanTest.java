package com.rocha.tacio.hangman;

import com.rocha.tacio.hangman.dictionary.DictionaryException;
import com.rocha.tacio.hangman.dictionary.Language;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by etacroc on 11/10/15.
 */
public class HangmanTest {

    @Test
    public void printsDrawingAccordingToCurrentStageOfHangman() throws GameOverException, DictionaryException {
        Hangman hangman = new Hangman();
        assertEquals("     __________\n    |          |\n    |\n    |\n    |\n    |\n    |\n    |\n    |\n    |\n" +
                "    |\n    |\n    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        " +
                "[    ]\n    |         \\  /\n    |          \\\n    |\n    |\n    |\n    |\n    |\n    |\n    |\n" +
                "    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o   ]\n" +
                "    |         \\  /\n    |          \\\n    |\n    |\n    |\n    |\n    |\n    |\n" +
                "    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                "    |         \\  /\n    |          \\\n    |\n    |\n    |\n    |\n    |\n    |\n" +
                "    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                "    |         \\ 7/\n    |          \\\n    |\n    |\n    |\n    |\n    |\n    |\n" +
                "    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                "    |         \\ 7/\n    |          \\0\n    |\n    |\n    |\n    |\n    |\n    |\n" +
                "    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                "    |         \\ 7/\n    |          \\0\n    |          |\n    |          |\n" +
                "    |          |\n    |          |\n    |\n    |\n    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                "    |         \\ 7/\n    |          \\0\n    |         _|\n    |        / |\n" +
                "    |       /  |\n    |          |\n    |\n    |\n    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                "    |         \\ 7/\n    |          \\0\n    |         _|_\n    |        / | \\\n" +
                "    |       /  |  \\\n    |          |\n    |\n    |\n    |\n    |\n    |\n", hangman.printHangman());
        hangman.miss();
        assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                "    |         \\ 7/\n    |          \\0\n    |         _|_\n    |        / | \\\n" +
                "    |       /  |  \\\n    |         _|\n    |        /\n    |       /\n    |      /\n" +
                "    |\n    |\n", hangman.printHangman());
        try {
            hangman.miss();
        } catch (GameOverException e) {
            assertEquals("     __________\n    |          |\n    |        wwwwww\n    |        [o  o]\n" +
                    "    |         \\ 7/\n    |          \\0\n    |         _|_\n    |        / | \\\n" +
                    "    |       /  |  \\\n    |         _|_\n    |        /   \\\n    |       /     \\\n" +
                    "    |      /       \\\n    |\n    |\n", hangman.printHangman());
        }
    }

    @Test
    public void recordAMiss() throws GameOverException, DictionaryException {
        Hangman hangman = new Hangman();
        assertEquals(0, hangman.getWrongPicks());
        hangman.miss();
        assertEquals(1, hangman.getWrongPicks());
    }

    @Test
    public void returnsMaximumAllowedErrors() throws DictionaryException {
        Hangman hangman = new Hangman();
        assertEquals(11, hangman.getMaxMisses());
    }

    @Test
    public void isGameOverTest() throws DictionaryException {
        Hangman hangman = new Hangman();
        assertFalse(hangman.isGameOver());
        for (int i = 0; i < hangman.getMaxMisses(); i++) {
            try {
                hangman.miss();
                assertFalse(hangman.isGameOver());
            } catch (GameOverException e) {
                assertEquals("Game Over!", e.getMessage());
                assertTrue(hangman.isGameOver());
            }
        }
    }

    @Test
    public void gameIsOverByVictory() throws GameOverException, DuplicateGuessException, DictionaryException {
        Hangman hangman = new Hangman();
        hangman.guess(hangman.getSecretWord());
        assertTrue(hangman.isGameOver());
    }

    @Test(expected = GameOverException.class)
    public void throwsGameOverExceptionWhenHangManIsComplete() throws GameOverException, DictionaryException {
        Hangman hangman = new Hangman();
        for (int i = 0; i < hangman.getMaxMisses(); i++) {
            hangman.miss();
        }
        fail();
    }

    @Test
    public void secretWordIsChosenUponGameStart() throws DictionaryException {
        Hangman hangman = new Hangman();
        assertNotNull(hangman.getSecretWord());
    }

    @Test
    public void userCanGuessLetters() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman();
        String firstLetterInSecretWord = hangman.getSecretWord().substring(0, 1);
        hangman.guess(firstLetterInSecretWord);
    }

    @Test(expected = DuplicateGuessException.class)
    public void returnWarningIfTheUserSelectsLetterAlreadyPicked() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman();
        String firstLetterInSecretWord = hangman.getSecretWord().substring(0, 1);
        hangman.guess(firstLetterInSecretWord);
        hangman.guess(firstLetterInSecretWord);
        fail();
    }

    @Test
    public void allowsToSelectDictionaryLanguage() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman(Language.EN);
    }

    @Test
    public void userCanGuessWord() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman(Language.EN);
        String secretWord = hangman.getSecretWord();
        hangman.guess("foo");
        assertTrue(!hangman.wordWasGuessed());
        hangman.guess(secretWord);
        assertTrue(hangman.wordWasGuessed());
    }

    @Test
    public void pickingAlreadyPickedLetterForTheSecondTimeCountsAsError() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman();
        String firstLetterInSecretWord = hangman.getSecretWord().substring(0, 1);
        hangman.guess(firstLetterInSecretWord);
        assertEquals(0, hangman.getWrongPicks());
        try {
            hangman.guess(firstLetterInSecretWord);
            assertEquals(0, hangman.getWrongPicks());
        } catch (DuplicateGuessException e) {
            assertEquals("You've guessed the letter '" + firstLetterInSecretWord +
                    "' twice! Next time it'll be considered a miss.", e.getMessage());
        }
        hangman.guess(firstLetterInSecretWord);
        assertEquals(1, hangman.getWrongPicks());
    }

    @Test
    public void pickingALetterNotInWordCountsAsError() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman();
        String aLetterNotInWord = pickFirstLetterNotInWord(hangman.getSecretWord());
        hangman.guess(aLetterNotInWord);
        assertEquals(1, hangman.getWrongPicks());
    }

    private String pickFirstLetterNotInWord(String word) {
        String alphabet[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y", "z"};
        for(String letter: alphabet){
            if (!word.toLowerCase().contains(letter)){
                return letter;
            }
        }
        return null;
    }

    @Test
    public void pickingNonLetterCharacterCountsAsError() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman();
        hangman.guess("2");
        hangman.guess(".");
        hangman.guess("#");
        assertEquals(3, hangman.getWrongPicks());
    }

    @Test
    public void printBlanksAndLettersTest() throws DictionaryException, GameOverException, DuplicateGuessException {
        Hangman hangman = new Hangman();
        String secretWord = hangman.getSecretWord();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            builder.append("__ ");
        }
        assertEquals(builder.toString(),hangman.printBlanksAndLetters());
    }

}
