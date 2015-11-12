package com.rocha.tacio.hangman;

/**
 * Created by etacroc on 11/11/15.
 */
public class SecretWord {
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
