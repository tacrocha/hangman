package com.rocha.tacio.hangman;

import com.rocha.tacio.hangman.dictionary.DictionaryException;
import com.rocha.tacio.hangman.dictionary.Language;
import com.rocha.tacio.hangman.dictionary.SecretWordDictionary;
import com.rocha.tacio.hangman.dictionary.SecretWordDictionaryImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by etacroc on 11/10/15.
 */
public class SecretWordDictionaryImplTest {

    @Test
    public void getSecretWordTest() throws DictionaryException {
        SecretWordDictionary dictionary = new SecretWordDictionaryImpl();
        List<String> englishDictionary = ((SecretWordDictionaryImpl) dictionary).getDictionary(Language.EN);
        String secretWord = dictionary.getSecretWord(Language.EN);
        System.out.println(secretWord);
        assertTrue(englishDictionary.contains(secretWord));
    }

    // TODO: Write tests to make the process of loading words more robust (e.g. ignore blank lines, non-words...)

}
