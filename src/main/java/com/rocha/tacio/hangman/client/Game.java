package com.rocha.tacio.hangman.client;

import com.rocha.tacio.hangman.DuplicateGuessException;
import com.rocha.tacio.hangman.GameOverException;
import com.rocha.tacio.hangman.Hangman;
import com.rocha.tacio.hangman.dictionary.DictionaryException;

import java.util.Scanner;

/**
 * Created by etacroc on 11/10/15.
 */
public class Game {

    private static Hangman hangman;

    public static void main(String[] args) {

        try {
            hangman = new Hangman();
        } catch (DictionaryException e) {
            System.out.println("Failed to start game. Could not load dictionary.");
        }

        while (!hangman.isGameOver()) {
            System.out.println(hangman.printHangman());
            System.out.println(hangman.printBlanksAndLetters());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Pick a letter or guess a word: ");
            String guess = scanner.nextLine();
            try {
                hangman.guess(guess);
            } catch (GameOverException e) {
                System.out.println(hangman.printHangman());
                System.out.println("Game over! Secret word was: " + hangman.getSecretWord());
            } catch (DuplicateGuessException e) {
                System.out.println(e.getMessage());
            }
        }

        if (hangman.wordWasGuessed()) {
            System.out.println("Victory! You guessed the secret word: " + hangman.getSecretWord());
        }
    }

}
