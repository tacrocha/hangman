package com.rocha.tacio.hangman.dictionary;

/**
 * Created by etacroc on 11/10/15.
 */
public interface SecretWordDictionary {
    String getSecretWord();

    String getSecretWord(Language language);
}
