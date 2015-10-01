package com.jmadden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A "word" (which need not be a dictionary word, but must consist of 1-20 uppercase US characters)
 * with an associated rank indicating its position in a sorted set of all permutations of its characters.
 */
public class RankedWord {
    public RankedWord(String word) {
        isValid = word != null && word.matches("^[A-Z]+$");
        this.word = word;
    }

    /**
     * @return whether or not this word can be ranked; i.e. it consists of 1-20 uppercase US characters.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * @return the word's text.
     */
    public String getText() {
        return word;
    }

    /**
     * @return the word's rank in a sorted set of all permutations of its characters.
     * @throws IllegalStateException if the word is not valid according to isValid.
     */
    public long getRank() throws IllegalStateException {
        if (!isValid()) {
            String msg = "Cannot get the rank of an invalid word. ";
            msg += "Valid words are not null and contain only alphabetic characters. ";
            msg += "For example, 'ancillary', 'tusk', and 'pujn' are all valid.";
            throw new IllegalStateException(msg);
        }

        if (rank < 0) {
            // Note: add one to conform to problem spec, in which ranks begin at 1
            rank = calculateRank(getText()) + 1;
        }

        return rank;
    }

    // recursively work our way down the word, shaving of the first character each time.
    // for each substring `s` acquired in this manner, calculate the number of entries
    // before the first one beginning with s[0]. The rank is the sum of the entry counts
    // for each substring of the word.
    // Note: this algorithm produces a zero-based index,
    // so 1 must be added to conform to the problem specification
    private static long calculateRank(String text) {
        int length = text.length();
        if (length == 1) {
            return 0;
        }

        char first = text.charAt(0);
        char[] chars = text.toCharArray();

        Arrays.sort(chars);

        // count the number of entries appearing before
        // the first entry beginning with `first`
        long entriesBeforeWord = 0;
        char last = '\0';
        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];

            if (c == first) {
                break;
            }

            // if the word contains duplicate letters, don't double count them
            if (c == last) {
                continue;
            }

            // list of characters from word excluding the one we're counting entries for
            // the number of ways to permute this list is the number of entries for the character
            List<Character> subgroup = new ArrayList<>();
            for (int j = 0; j < chars.length; ++j) {
                char cc = chars[j];

                if (i != j) {
                    subgroup.add(cc);
                }
            }

            last = c;
            entriesBeforeWord += countPermutations(subgroup);
        }

        return entriesBeforeWord + calculateRank(text.substring(1));
    }

    // calculate the number of permutations of a given list of characters, accounting for repeats
    private static long countPermutations(List<Character> chars) {
        HashMap<Character, Integer> charCounts = new HashMap<>();

        for (char c : chars) {
            if (charCounts.containsKey(c)) {
                charCounts.put(c, charCounts.get(c) + 1);
            } else {
                charCounts.put(c, 1);
            }
        }

        long denominator = 1;
        for (char c : charCounts.keySet()) {
            int count = charCounts.get(c);
            denominator *= factorial(count);
        }

        return factorial(chars.size()) / denominator;
    }

    // naive factorial implementation -- could do a lot better but it does the job
    private static long factorial(long n) {
        if (n == 0) {
            return 1;
        }

        long result = 1;
        for (long i = 2; i <= n; ++i) {
            result *= i;
        }

        return result;
    }

    @Override
    public String toString() {
        return getText();
    }

    private boolean isValid = false;
    private String word = null;
    private long rank = -1;
}
