package com.rocha.tacio.hangman.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by etacroc on 11/10/15.
 */
public class SecretWordDictionaryImpl implements SecretWordDictionary {

    private static final Map<Language, List<String>> dictionaries = new HashMap<>();
    private static final Language defaultLang = Language.EN;

    public SecretWordDictionaryImpl() throws DictionaryException {
        try {
            URL url = getClass().getResource("englishWords.txt");
            dictionaries.put(Language.EN, loadFromFile(url));
        } catch (IOException e) {
            throw new DictionaryException(e);
        }
    }

    @Override
    public String getSecretWord() {
        return getSecretWord(defaultLang);
    }

    @Override
    public String getSecretWord(Language language) {
        Random random = new Random();
        int index = random.nextInt(dictionaries.get(language).size());
        return dictionaries.get(language).get(index);
    }

    public List<String> getDictionary(Language language) {
        return this.dictionaries.get(language);
    }

    private List<String> loadFromFile(URL url) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.trim());
        }
        return words;
    }

}
