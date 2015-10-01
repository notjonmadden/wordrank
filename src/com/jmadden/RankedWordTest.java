package com.jmadden;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class RankedWordTest {
    @Test
    public void nullAndEmptyStringsAreInvalid() {
        RankedWord nullWord = new RankedWord(null);
        RankedWord emptyWord = new RankedWord("");

        assertFalse("null strings should not be allowed", nullWord.isValid());
        assertFalse("empty strings should not be allowed", emptyWord.isValid());
    }

    @Test
    public void nonAlphabeticStringsAreInvalid() {
        RankedWord nonAlphaWord = new RankedWord("Hello, world!");
        assertFalse("strings with non-alphabetic characters should not be allowed", nonAlphaWord.isValid());
    }

    @Test
    public void nonEmptyAlphabeticStringsAreValid() {
        RankedWord word1 = new RankedWord("HELLO");
        RankedWord word2 = new RankedWord("GOODBYE");
        RankedWord word3 = new RankedWord("FREWDAEQ");

        assertTrue(word1.isValid());
        assertTrue(word2.isValid());
        assertTrue(word3.isValid());
    }

    @Test
    public void sampleWordsRankCorrectly() {
        RankedWord abab = new RankedWord("ABAB");
        RankedWord aaab = new RankedWord("AAAB");
        RankedWord baaa = new RankedWord("BAAA");
        RankedWord question = new RankedWord("QUESTION");
        RankedWord bookKeeper = new RankedWord("BOOKKEEPER");
        RankedWord longOne = new RankedWord("NONINTUITIVENESS");

        assertEquals(2L, abab.getRank());
        assertEquals(1L, aaab.getRank());
        assertEquals(4L, baaa.getRank());
        assertEquals(24572L, question.getRank());
        assertEquals(10743L, bookKeeper.getRank());
        assertEquals(8222334634L, longOne.getRank());
    }

    @Test(timeout=500)
    public void runsInLessThan500Millis() {
        // I'm guessing that the last entry will exhibit worst-case behavior
        RankedWord longWord = new RankedWord("TSRQPONMLKJIHGFEDCBA");
        longWord.getRank();
    }
}