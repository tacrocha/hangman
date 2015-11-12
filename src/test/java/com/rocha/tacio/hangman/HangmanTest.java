package com.rocha.tacio.hangman;

import com.rocha.tacio.hangman.dictionary.DictionaryException;
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
        assertEquals(0, hangman.getStage());
        hangman.miss();
        assertEquals(1, hangman.getStage());
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

    

}
