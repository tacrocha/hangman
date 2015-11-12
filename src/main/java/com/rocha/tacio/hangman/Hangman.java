package com.rocha.tacio.hangman;

import com.rocha.tacio.hangman.dictionary.DictionaryException;
import com.rocha.tacio.hangman.dictionary.Language;
import com.rocha.tacio.hangman.dictionary.SecretWordDictionary;
import com.rocha.tacio.hangman.dictionary.SecretWordDictionaryImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by etacroc on 11/10/15.
 */
public class Hangman {

    private static final String drawing[][] = {
            {"     __________", "    |          |", "    |", "    |", "    |", "    |", "    |", "    |", "    |",
                    "    |", "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [    ]", "    |         \\  /",
                    "    |          \\", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o   ]", "    |         \\  /",
                    "    |          \\", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\  /",
                    "    |          \\", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\ 7/",
                    "    |          \\", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\ 7/",
                    "    |          \\0", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\ 7/",
                    "    |          \\0", "    |          |", "    |          |", "    |          |", "    |          |",
                    "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\ 7/",
                    "    |          \\0", "    |         _|", "    |        / |", "    |       /  |", "    |          |",
                    "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\ 7/",
                    "    |          \\0", "    |         _|_", "    |        / | \\", "    |       /  |  \\",
                    "    |          |", "    |", "    |", "    |", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\ 7/",
                    "    |          \\0", "    |         _|_", "    |        / | \\", "    |       /  |  \\",
                    "    |         _|", "    |        /", "    |       /", "    |      /", "    |", "    |"},
            {"     __________", "    |          |", "    |        wwwwww", "    |        [o  o]", "    |         \\ 7/",
                    "    |          \\0", "    |         _|_", "    |        / | \\", "    |       /  |  \\",
                    "    |         _|_", "    |        /   \\", "    |       /     \\", "    |      /       \\",
                    "    |", "    |"}
    };

    private AtomicInteger count = new AtomicInteger(0);
    private Map<String, Integer> guesses = new HashMap<>();
    private boolean victory = false;
    private SecretWordDictionary dictionary;
    private SecretWord secretWord;

    public Hangman() throws DictionaryException {
        this.dictionary = new SecretWordDictionaryImpl();
        this.secretWord = new SecretWord(dictionary.getSecretWord());
    }

    public Hangman(Language language) throws DictionaryException {
        this.dictionary = new SecretWordDictionaryImpl();
        this.secretWord = new SecretWord(dictionary.getSecretWord(language));
    }

    public void miss() throws GameOverException {
        if (isGameOver()) {
            throw new GameOverException("Game Over!");
        } else {
            this.count.incrementAndGet();
        }
        if (isGameOver()) {
            throw new GameOverException("Game Over!");
        }
    }

    public int getStage() {
        return count.get();
    }

    public int getMaxMisses() {
        return drawing.length;
    }

    public String printHangman() {
        StringBuilder builder = new StringBuilder();
        for (String line : drawing[count.get()]) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }

    public String printBlanksAndLetters() {
        return secretWord.toString();
    }

    public boolean isGameOver() {
        return !(getStage() < drawing.length - 1) || victory;
    }

    public boolean isVictory() {
        return this.victory;
    }

    public String getSecretWord() {
        return this.secretWord.word.toUpperCase();
    }

    public void guess(String guess) throws GameOverException, DuplicateGuessException {
        if (guess.matches("\\w{1}")) {
            handleGuessLetter(guess);
        } else if (guess.matches("\\w{2,}")) {
            handleGuessWord(guess);
        } else {
            miss();
        }
    }

    private void handleGuessLetter(String letter) throws GameOverException, DuplicateGuessException {
        if (!penaltyForDuplicateGuessing(letter)) {
            if (secretWord.isLetterPresent(letter)) {
                secretWord.updateVisibleLetters(letter);
                victory = secretWord.foundAllLetters();
            } else {
                miss();
            }
        }
    }

    private boolean penaltyForDuplicateGuessing(String guessedLetter) throws GameOverException, DuplicateGuessException {
        boolean penalty = false;
        String lowerCaseLetter = guessedLetter.toLowerCase();
        if (guesses.get(lowerCaseLetter) == null) {
            guesses.put(lowerCaseLetter, 1);
        } else {
            if (guesses.get(lowerCaseLetter) >= 2) {
                penalty = true;
                miss();
            } else {
                guesses.put(lowerCaseLetter, guesses.get(lowerCaseLetter) + 1);
                throw new DuplicateGuessException("You've guessed the letter '" + guessedLetter + "' twice! Next time it'll be considered a miss.");
            }
        }
        return penalty;
    }

    private void handleGuessWord(String word) throws GameOverException {
        if (secretWord.word.equalsIgnoreCase(word)) {
            secretWord.showAllLetters();
            victory = true;
        } else {
            miss();
        }
    }

    private class SecretWord {
        String word;
        boolean[] visibleLetters;

        SecretWord(String word) {
            this.word = word;
            visibleLetters = new boolean[word.length()];
        }

        boolean isLetterPresent(String letter) {
            return word.toLowerCase().contains(letter.toLowerCase());
        }

        boolean[] updateVisibleLetters(String guessedLetter) {
            for (int i = 0; i < word.length(); i++) {
                String letterInWord = String.valueOf(word.charAt(i));
                if (guessedLetter.equalsIgnoreCase(letterInWord)) {
                    visibleLetters[i] = true;
                }
            }
            return visibleLetters;
        }

        void showAllLetters() {
            for (int i = 0; i < word.length(); i++) {
                visibleLetters[i] = true;
            }
        }

        boolean foundAllLetters() {
            boolean res = true;
            for (boolean visible : visibleLetters) {
                if (!visible) {
                    res = false;
                }
            }
            return res;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                if (visibleLetters[i]) {
                    builder.append(" ").append(word.toUpperCase().charAt(i)).append(" ");
                } else {
                    builder.append("__ ");
                }
            }
            return builder.toString();
        }
    }

}
