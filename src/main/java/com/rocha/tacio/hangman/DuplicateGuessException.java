package com.rocha.tacio.hangman;

/**
 * Created by etacroc on 11/11/15.
 */
public class DuplicateGuessException extends Exception {
    public DuplicateGuessException(String msg) {
        super(msg);
    }
}
