/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: // TODO: YOUR NAME HERE
 */

package tests;

import manchadoa.BasicWord;
import manchadoa.Bigram;
import manchadoa.Driver;
import manchadoa.VocabularyEntry;
import manchadoa.Word;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Unit Tests for Lab 13 - Text Processing
 */
public class Lab13Tests {
    private static final String[] CORRECT_WORDS = {"hello", "hello", "hello", "this", "is", "a",
            "test", "of", "nlp", "3000", "this", "is", "only", "a", "test"};
    /**
     * BasicWord Tests
     */
    @Test
    @DisplayName("BasicWord tests")
    public void basicWord() {
        BasicWord bw1 = new BasicWord("hello", 0);
        BasicWord bw2 = new BasicWord("penguin", 1);
        BasicWord bw3 = new BasicWord("hello", 2);
        BasicWord bw4 = new BasicWord("apple", 3);
        try {
            Field word = BasicWord.class.getSuperclass().getDeclaredField("word");
            Field location = BasicWord.class.getDeclaredField("location");
            word.setAccessible(true);
            location.setAccessible(true);
            Assertions.assertEquals("hello", word.get(bw1));
            Assertions.assertEquals(0L, location.get(bw1));
            Assertions.assertEquals(0L, bw1.getLocation());
            Assertions.assertThrows(UnsupportedOperationException.class, () -> bw1.addLocation(1));
            Assertions.assertEquals("hello", bw1.toString());
            Assertions.assertNotEquals(bw1, bw2);
            Assertions.assertEquals(bw1, bw3);
            Assertions.assertEquals(0, bw1.compareTo(bw3));
            Assertions.assertTrue(bw1.compareTo(bw2) < 0);
            Assertions.assertTrue(bw1.compareTo(bw4) > 0);
        } catch (NoSuchFieldException e) {
            Assertions.fail("Missing field: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Could not access underlying field.");
        }
    }

    /**
     * Bigram Tests
     */
    @Test
    @DisplayName("Bigram tests")
    @SuppressWarnings("unchecked")
    public void bigram() {
        BasicWord bw1 = new BasicWord("hello", 0);
        BasicWord bw2 = new BasicWord("penguin", 1);
        Bigram bg1 = new Bigram(bw1, bw2);
        Bigram bg2 = new Bigram(bw2, bw1);
        Bigram bg3 = new Bigram(bw1, bw2);
        Bigram bg4 = new Bigram(bw1, bw1);
        try {
            Field word = Bigram.class.getSuperclass().getDeclaredField("word");
            Field word2 = Bigram.class.getDeclaredField("word2");
            Field occurrences = Bigram.class.getDeclaredField("occurrences");
            Field locations = Bigram.class.getDeclaredField("locations");
            word.setAccessible(true);
            word2.setAccessible(true);
            occurrences.setAccessible(true);
            locations.setAccessible(true);
            Assertions.assertEquals("hello", word.get(bg1));
            Assertions.assertEquals("penguin", word2.get(bg1));
            Assertions.assertEquals(1, occurrences.get(bg1));
            Assertions.assertEquals(1, ((List<Long>) locations.get(bg1)).size());
            Assertions.assertEquals(0, ((List<Long>) locations.get(bg1)).get(0));
            Assertions.assertThrows(IllegalArgumentException.class, () -> bg1.addLocation(-1L));
            Assertions.assertThrows(IllegalArgumentException.class, () -> bg1.addLocation(0L));
            bg1.addLocation(3L);
            Assertions.assertEquals(2, ((List<Long>) locations.get(bg1)).size());
            Assertions.assertEquals(3, ((List<Long>) locations.get(bg1)).get(1));
            Assertions.assertEquals("hello           penguin            2", bg1.toString());
            Assertions.assertEquals(bg1, bg3);
            Assertions.assertNotEquals(bg1, bg2);
            Assertions.assertNotEquals(bg1, bg4);
            Assertions.assertEquals(0, bg2.compareTo(bg3));
            Assertions.assertTrue(bg1.compareTo(bg2) > 0);
            Assertions.assertTrue(bg3.compareTo(bg1) < 0);
        } catch (NoSuchFieldException e) {
            Assertions.fail("Missing field: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Could not access underlying field.");
        } catch (ClassCastException e) {
            Assertions.fail("Incorrect field type: " + e.getMessage());
        }
    }

    /**
     * VocabularyEntry Tests
     */
    @Test
    @DisplayName("VocabularyEntry tests")
    @SuppressWarnings("unchecked")
    public void vocabularyEntry() {
        VocabularyEntry v1 = new VocabularyEntry(new BasicWord("hello", 0));
        VocabularyEntry v2 = new VocabularyEntry(new BasicWord("penguin", 1));
        VocabularyEntry v3 = new VocabularyEntry(new BasicWord("hello", 2));
        try {
            Field word = VocabularyEntry.class.getSuperclass().getDeclaredField("word");
            Field occurrences = VocabularyEntry.class.getDeclaredField("occurrences");
            Field locations = VocabularyEntry.class.getDeclaredField("locations");
            word.setAccessible(true);
            occurrences.setAccessible(true);
            locations.setAccessible(true);
            Assertions.assertEquals("hello", word.get(v1));
            Assertions.assertEquals(1, occurrences.get(v1));
            Assertions.assertEquals(1, ((List<Long>) locations.get(v1)).size());
            Assertions.assertEquals(0, ((List<Long>) locations.get(v1)).get(0));
            Assertions.assertThrows(IllegalArgumentException.class, () -> v1.addLocation(-1L));
            Assertions.assertThrows(IllegalArgumentException.class, () -> v1.addLocation(0L));
            v1.addLocation(3L);
            Assertions.assertEquals(2, ((List<Long>) locations.get(v1)).size());
            Assertions.assertEquals(3, ((List<Long>) locations.get(v1)).get(1));
            Assertions.assertEquals("hello              2", v1.toString());
            Assertions.assertEquals(v1, v3);
            Assertions.assertNotEquals(v1, v2);
            Assertions.assertEquals(0, v2.compareTo(v3));
            Assertions.assertTrue(v1.compareTo(v2) > 0);
            Assertions.assertTrue(v3.compareTo(v1) < 0);
        } catch (NoSuchFieldException e) {
            Assertions.fail("Missing field: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Could not access underlying field.");
        } catch (ClassCastException e) {
            Assertions.fail("Incorrect field type: " + e.getMessage());
        }
    }

    /**
     * Main.getInput
     */
    @Test
    @DisplayName("Main.getInput")
    public void getInput() {
        final String testFolder = "tests";
        try {
            Method getInput = Driver.class.getDeclaredMethod("getInput",
                    Scanner.class, String.class);
            getInput.setAccessible(true);
            String output = (String) getInput.invoke(null, new Scanner(
                    new File("src/" + testFolder +
                            System.getProperty("file.separator") + "testInputs.txt")), "test");
            Assertions.assertEquals("inputFile input", output);
        } catch (NoSuchMethodException e) {
            Assertions.fail("Missing class " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not access the method " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate test file.");
        }
    }

    /**
     * Main.removeHeader
     */
    @Test
    @DisplayName("Main.removeHeader")
    public void removeHeader() {
        final String testFolder = "tests";
        try {
            Method removeHeader = Driver.class.getDeclaredMethod("removeHeader",
                    Scanner.class);
            removeHeader.setAccessible(true);
            Scanner read = new Scanner(new File("src/" + testFolder +
                    System.getProperty("file.separator") + "test.txt"));
            removeHeader.invoke(null, read);
            Assertions.assertEquals("Hello hello? hello.", read.nextLine());
        } catch (NoSuchMethodException e) {
            Assertions.fail("Missing class " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not access the method " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate test file.");
        } catch (NoSuchElementException e) {
            Assertions.fail("Scanner should not be empty after header");
        }
    }

    /**
     * Main.normalize
     */
    @Test
    @DisplayName("Main.normalize")
    public void normalize() {
        try {
            Method normalize = Driver.class.getDeclaredMethod("normalize", String.class);
            normalize.setAccessible(true);
            Assertions.assertEquals("penguin", normalize.invoke(null, "penguin"));
            Assertions.assertEquals("penguin", normalize.invoke(null, "Penguin"));
            Assertions.assertEquals("penguin", normalize.invoke(null, "penguin!"));
            Assertions.assertEquals("penguin", normalize.invoke(null, "penguin?"));
            Assertions.assertEquals("penguin", normalize.invoke(null, "penguin;"));
            Assertions.assertEquals("penguin", normalize.invoke(null, "PeNgUiN"));
            Assertions.assertEquals("", normalize.invoke(null, "..."));
        } catch (NoSuchMethodException e) {
            Assertions.fail("Missing class " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not access the method " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main.addWords
     */
    @Test
    @DisplayName("Main.addWords")
    public void addWords() {
        final String testFolder = "tests";
        try {
            final int correctListSize = 15;

            // remove header
            Method removeHeader = Driver.class.getDeclaredMethod("removeHeader",
                    Scanner.class);
            removeHeader.setAccessible(true);
            Scanner read = new Scanner(new File("src/" + testFolder +
                    System.getProperty("file.separator") + "test.txt"));
            removeHeader.invoke(null, read);
            // add Words
            List<BasicWord> words = new ArrayList<>();
            Method addWords = Driver.class.getDeclaredMethod("addWords",
                    List.class, Scanner.class);
            addWords.setAccessible(true);
            addWords.invoke(null, words, read);
            Assertions.assertEquals(correctListSize, words.size());
            for(int i = 0; i < words.size(); ++i) {
                if(!words.get(i).equals(new BasicWord(CORRECT_WORDS[i], i))) {
                    Assertions.fail("Word at " + i + " should have been " + CORRECT_WORDS[i] +
                            " but was " + words.get(i));
                }
            }
        } catch (NoSuchMethodException e) {
            Assertions.fail("Missing class " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not access the method " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate test file.");
        } catch (NoSuchElementException e) {
            Assertions.fail("Scanner should not be empty after header");
        }
    }

    /**
     * Main.addBigrams
     */
    @Test
    @DisplayName("Main.addBigrams")
    public void addBigrams() {
        try {
            final int correctListSize = 11;
            // add Words
            List<BasicWord> words = preTest();
            List<Word> bigrams = new ArrayList<>();
            Method addBigrams = Driver.class.getDeclaredMethod("addBigrams",
                    List.class, List.class);
            addBigrams.setAccessible(true);
            addBigrams.invoke(null, bigrams, words);
            Assertions.assertEquals(correctListSize, bigrams.size());
            Assertions.assertEquals("hello           hello              2",
                    bigrams.get(0).toString());
            Assertions.assertEquals("is              a                  1",
                    bigrams.get(3).toString());
        } catch (NoSuchMethodException e) {
            Assertions.fail("Missing class " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not access the method " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main.addVocabulary
     */
    @Test
    @DisplayName("Main.addVocabulary")
    public void addVocabulary() {
        try {
            final int correctListSize = 9;
            final long secondThis = 10L;
            // add Words
            List<BasicWord> words = preTest();
            List<Word> vocabulary = new ArrayList<>();
            Method addVocabulary = Driver.class.getDeclaredMethod("addVocabulary",
                    List.class, List.class);
            addVocabulary.setAccessible(true);
            addVocabulary.invoke(null, vocabulary, words);
            Assertions.assertEquals(correctListSize, vocabulary.size());
            Assertions.assertEquals(3, ((VocabularyEntry)vocabulary.get(0)).getOccurrences());
            Assertions.assertEquals(secondThis,
                    ((VocabularyEntry) vocabulary.get(1)).getLocations().get(1));
            System.out.println(((VocabularyEntry)vocabulary.get(1)).getLocations());
        } catch (NoSuchMethodException e) {
            Assertions.fail("Missing class " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not access the method " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main.report
     */
    @Test
    @DisplayName("Main.report")
    public void report() {
        final int topThree = 3;
        final int topFive = 5;
        final int topSeven = 7;
        try {
            String[] arr = {"cobb", "caesar", "spinach", "house", "potato"};
            List<Word> list = new ArrayList<>();
            for(int i = 0; i < arr.length; ++i) {
                list.add(new BasicWord(arr[i], i));
            }
            Method report = Driver.class.getDeclaredMethod("report", List.class, String.class,
                    int.class);
            report.setAccessible(true);
            PrintStream oldOut = System.out;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            System.setOut(ps);
            report.invoke(null, list, "salad", topThree);
            String output1 = os.toString();
            os.reset();
            report.invoke(null, list, "salads", topFive);
            String output2 = os.toString();
            os.reset();
            report.invoke(null, list, "salads", topSeven);
            String output3 = os.toString();
            System.setOut(oldOut);
            Assertions.assertEquals("""
                    Top 3 salad are:
                    1                     cobb
                    2                   caesar
                    3                  spinach
                    """, output1);
            Assertions.assertEquals("""
                    Top 5 salads are:
                    1                     cobb
                    2                   caesar
                    3                  spinach
                    4                    house
                    5                   potato
                    """, output2);
            Assertions.assertEquals("""
                    Top 7 salads are:
                    1                     cobb
                    2                   caesar
                    3                  spinach
                    4                    house
                    5                   potato
                    """, output3);
        } catch (NoSuchMethodException e) {
            Assertions.fail("Missing class " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.err.println("Could not access the method " + e.getMessage());
            e.printStackTrace();
        }
    }
    private List<BasicWord> preTest() {
        List<BasicWord> list = new ArrayList<>();
        for(int i = 0; i < CORRECT_WORDS.length; ++i) {
            list.add(new BasicWord(CORRECT_WORDS[i], i));
        }
        return list;
    }
}
