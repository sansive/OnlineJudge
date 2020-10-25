/**
 * 843 - Crypt Kicker 
 * Time limit: 3.000 seconds
 * 
 * @author Sandra Sierra
 */

package p843;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

class Main {
    static String [] dictionary;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedInputStream(System.in, 4*1024))) {
            // Get dictionary
            int n = sc.nextInt();
            dictionary = new String[n];
            for (int i=0; i<n; i++)
                dictionary[i] = sc.next();

            // Get encrypted lines
            sc.nextLine();
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();

                // Get encrypted words
                Scanner sc_line = new Scanner(line);
                ArrayList<String> encryptedWords = new ArrayList<String>();
                while (sc_line.hasNext())
                    encryptedWords.add(sc_line.next());
                sc_line.close();

                // Get decrypted words
                ArrayList<String> decryptedWords = decrypt(encryptedWords);

                // Output
                for (int i = 0; i < decryptedWords.size(); i++) {
                    if (i != decryptedWords.size() - 1) System.out.print(decryptedWords.get(i) + " ");
                    else System.out.println(decryptedWords.get(i));
                }
            }

            sc.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * It decrypts each word contained in the given list.
     * 
     * @param encryptedWords: list of words from an encrypted line
     * 
     * @return  List of decrypted words. If there is no solution,
     *          replace every letter of the alphabet by an asterisk.
     */
    private static ArrayList<String> decrypt(ArrayList<String> encryptedWords) {
        ArrayList<String> decryptedWords = new ArrayList<String>();

        // In order to make the algorithm more eficient:
        // Get copy of the encryptedWords
        ArrayList<String> copy = new ArrayList<String>(encryptedWords);
        // Sort "copy" by length (from the greatest to the smallest)
        Collections.sort(copy, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(b.length(), a.length());
            }
        });

        // Initialize an array to hold the replacements that will decrypt the line
        char[] replacements = new char[26];
        for (int i = 0; i < 26; i++) replacements[i] = '*';

        // Restore original replacements if there is no solution
        if (!match(copy, replacements))
            for (int i = 0; i < 26; i++) replacements[i] = '*';

        // Decrypt line using the array with the replacements
        for (int i = 0; i < encryptedWords.size(); i++) {
            String decryptedWord = "";
            for (int j = 0; j < encryptedWords.get(i).length(); j++)
                decryptedWord = decryptedWord + replacements[encryptedWords.get(i).charAt(j) - 97];
            decryptedWords.add(decryptedWord);
        }

        return decryptedWords;
    }


    /**
     * It finds a word from the dictionary that matches a given word.
     * 
     * @param encryptedWords: list of words from an encrypted line
     * @param replacements: set of replacements to decrypt a word
     * 
     * @return  true    If there is a word from the dictionary that matches the given one
     *          false   If not
     */
    private static boolean match(ArrayList<String> encryptedWords, char[] replacements) {
        String encryptedWord = encryptedWords.get(0);

        // Get copy of the current state of replacements
        char[] originalReplacements = new char[26];
        for (int i=0; i<26; i++) originalReplacements[i] = replacements[i];

        // Get a list of words that can match
        ArrayList<String> posibleWords = new ArrayList<>();
        for (String word : dictionary)
            if (mayMatch(word, encryptedWord)) posibleWords.add(word);

        // Check if there is a suitable mapping
        for (String word : posibleWords) {
            if (mapWord(encryptedWord, word, replacements)) {
                if (encryptedWords.size() > 1) {
                    // Recursive call
                    encryptedWords.remove(0);
                    if (match(encryptedWords, replacements)) return true;
                    else {
                        encryptedWords.add(0, encryptedWord);
                        // Restore original replacements
                        for (int i=0; i<26; i++) replacements[i] = originalReplacements[i];
                    }
                } else return true;
            }
        }

        return false;
    }


    /**
     * It checks if two words may match acording to their lenghts and patterns.
     * 
     * @param word
     * @param encryptedWord
     * 
     * @return  true    If the words may match
     *          false   If not
     */
    private static boolean mayMatch(String word, String encryptedWord) {
        // Check lenghts
        if (word.length() == encryptedWord.length()) {
            // Check patterns
            String A = "";
            String B = "";
            int counterA = 0;
            int counterB = 0;
            Map<Character, Integer> mappingA = new HashMap<Character, Integer>();
            Map<Character, Integer> mappingB = new HashMap<Character, Integer>();

            for (int i = 0; i < word.length(); i++) {
                if (!mappingA.containsKey(word.charAt(i))) {
                    mappingA.put(word.charAt(i), counterA++);
                }
                A = A + mappingA.get(word.charAt(i));
                if (!mappingB.containsKey(encryptedWord.charAt(i))) {
                    mappingB.put(encryptedWord.charAt(i), counterB++);
                }
                B = B + mappingB.get(encryptedWord.charAt(i));
            }

            if (A.equals(B)) return true;
            return false;            
        }

        return false;
    }


    /**
     * It maps the words in the set of replacements.
     * 
     * @param word
     * @param encryptedWord
     * @param replacements: set of replacements to decrypt a word
     * 
     * @return  true    If the mapping of the words has been correct
     *          false   If not
     */
    private static boolean mapWord(String encryptedWord, String word, char[] replacements) {
        // Get copy of the current state of replacements
        char[] originalReplacements = new char[26];
        for (int i=0; i<26; i++) originalReplacements[i] = replacements[i];

        // Check mapping
        for (int i = 0; i < encryptedWord.length(); i++) {
            // Check if letters have been already mapped
            char a = encryptedWord.charAt(i);
            char b = word.charAt(i);
            for (int j = 0; j < 26; j++) {
                if (replacements[j] == b && j != (a - 97)) {
                    // Restore original replacements
                    for (int k=0; k<26; k++) replacements[k] = originalReplacements[k];
                    return false;
                }
            }

            // Check value in replacements
            int index = a - 97;
            if (replacements[index] == '*') {
                // Update replacements
                replacements[index] = word.charAt(i);
            }
            if (replacements[index] != word.charAt(i)) {
                // Restore original replacements
                for (int j=0; j<26; j++) replacements[j] = originalReplacements[j];
                return false;
            }
        }

        return true;
    }
}