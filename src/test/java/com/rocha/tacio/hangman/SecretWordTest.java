package com.rocha.tacio.hangman;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by etacroc on 11/11/15.
 */
public class SecretWordTest {

    @Test
    public void canDisplayUndescoresOrTheCorrectlyGuessedLetters() {
        String word = "secret";
        SecretWord secretWord = new SecretWord(word);
        assertEquals("__ __ __ __ __ __ ", secretWord.toString());
        secretWord.updateVisibleLetters("s");
        assertEquals(" S __ __ __ __ __ ", secretWord.toString());
        secretWord.updateVisibleLetters("e");
        assertEquals(" S  E __ __  E __ ", secretWord.toString());
        secretWord.updateVisibleLetters("T");
        secretWord.showAllLetters();
        assertEquals(" S  E  C  R  E  T ", secretWord.toString());
    }

}
