/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Adrian Manchado
 */
package manchadoa;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * A Driver class for processing text from a file.
 */
public class Driver {
    private static final String DATA_FOLDER = "data";
    public static void main(String[] args) {
        // Instantiate your collections and other variables here
        Scanner in = new Scanner(System.in);
        List<BasicWord> basicWordList = new ArrayList<>();
        List<Word> bigramList = new ArrayList<>();
        List<Word> vocabularyList = new ArrayList<>();
        int numberOfEntries = 0;
        // ask user for file
        String fileName = getInput(in, "Enter the file to read:");
        // read file into Scanner
        try {
            System.out.println("Processing...");
            Scanner read = new Scanner(Path.of(DATA_FOLDER+ "/" + fileName));
            // trim heading out of file
            removeHeader(read);
            // generate words and add to a list
            System.out.println("Getting the words...");
            addWords(basicWordList, read);
            // generate bigrams from list
            System.out.println("Making bigrams...");
            addBigrams(bigramList, basicWordList);
            // generate vocabulary from list
            System.out.println("Generating vocabulary...");
            addVocabulary(vocabularyList, basicWordList);
            // sort the data
            System.out.println("Sorting lists...");
            bigramList.sort(Collections.reverseOrder());
            vocabularyList.sort(Collections.reverseOrder());
            // Save vocabulary as a text file
            System.out.println("Saving vocabulary...");
            saveFile(vocabularyList, new File(getInput(in, "Enter the vocabulary file to save:")));
            // Save bigrams as a text file
            System.out.println("Saving bigrams...");
            saveFile(bigramList, new File(getInput(in, "Enter the bigram file to save:")));
            // generate reports
            System.out.println("Generating reports...");
            // Ask for how many top entries to show
            numberOfEntries = Integer.parseInt(getInput(in,
                    "Enter the number of top entries to show:"));
            // report the top entries for vocabulary
            report(vocabularyList, "vocabulary", numberOfEntries);
            // report the top entries for bigrams
            report(bigramList, "bigrams", numberOfEntries);
        } catch (IOException e){
            System.out.println("That file does not exist");
        } catch (UnsupportedOperationException e){
            System.out.println("Something went wrong");
        }



    }

    /**
     * A helper method to get input from the user.
     * @param in - Scanner using System.in as input
     * @param message - the message with which to prompt the user
     * @return the user input
     */
    private static String getInput(Scanner in, String message){
        System.out.print(message + " ");
        return in.nextLine();
    }

    /**
     * A helper method to remove the header information from a Project
     * Gutenberg file. The method will continue to consume the buffer
     * of the Scanner until the header text has been removed, then will stop.
     * @param read Scanner using a Project Gutenberg text file as input
     */
    private static void removeHeader(Scanner read){
        String line = read.nextLine();
        while (!line.startsWith("***")){
            line = read.nextLine();
        }
    }

    /**
     * A helper method that reads words from a text file one at a time and
     * stores the normalized words in a List of BasicWords. Any word that
     * contains only whitespace should be ignored.
     * @param words the List used to store the words
     * @param read Scanner using a Project Gutenberg text file as input
     */
    private static void addWords(List<BasicWord> words, Scanner read){
        int location = 0;
        String initialWord = "";
        while (read.hasNext() && !initialWord.equalsIgnoreCase("***")){
            initialWord = read.next();
            String normalizedWord = normalize(initialWord);
            if (!normalizedWord.isBlank()) {
                BasicWord wordBasic = new BasicWord(normalizedWord, location);
                words.add(wordBasic);
                location++;
            }
        }
    }

    /**
     * A helper method that removes all punctuation from a String and
     * converts the resulting punctuation-less String to lowercase
     * @param s the String to normalize
     * @return the normalized String
     */
    private static String normalize(String s){
        s = s.replaceAll("\\p{Punct}", "");
        s = s.toLowerCase();
        return s;
    }

    /**
     * A helper method that generates Bigrams from the ordered List of
     * BasicWords and stores the Bigrams in a List. There should only be
     * one instance of each Bigram in the List. When successive copies of
     * the same Bigram are found, the location should be added to the
     * existing Bigram and the occurrence count should be incremented.
     * @param bigrams the List in which to store the resulting Bigrams
     * @param words the ordered List of BasicWord to use to generate the Bigrams
     */
    private static void addBigrams(List<Word> bigrams, List<BasicWord> words){
        boolean isRepeated = false;
        for (BasicWord word: words){
            if (word.getLocation() < words.size()-1) {
                Bigram bigramObj = new Bigram(word, words.get((int) (word.getLocation() + 1)));
                for (Word bigram : bigrams) {
                    if (bigram.equals(bigramObj)) {
                        bigram.addLocation(word.getLocation());
                        isRepeated = true;
                    }
                }
                if (!isRepeated) {
                    bigrams.add(bigramObj);
                }
                isRepeated = false;
            }
        }
    }

    /**
     * A helper method that generates VocabularyEntry objects from the
     * ordered List of BasicWords and stores the entries in a List. There
     * should only be one instance of each VocabularyEntry in the List. When
     * successive copies of the same entry are found, the location should be
     * added to the existing entry and the occurrence count should be incremented.
     * @param vocabulary the List in which to store the resulting VocabularyEntry
     *                   objects
     * @param words  the ordered List of BasicWord to use to generate the vocabulary
     */
    private static void addVocabulary(List<Word> vocabulary, List<BasicWord> words){
        boolean isRepeated = false;
        for (BasicWord word: words){
            if (word.getLocation() < words.size()) {
                VocabularyEntry vocabEntryWord = new VocabularyEntry(word);
                for (Word vocabularyWord : vocabulary) {
                    if (vocabularyWord.equals(vocabEntryWord)) {
                        vocabularyWord.addLocation(word.getLocation());
                        isRepeated = true;
                    }
                }
                if (!isRepeated) {
                    vocabulary.add(vocabEntryWord);
                }
                isRepeated = false;
            }
        }

    }

    /**
     * A helper method to save a List of Words as a text file
     * @param list the List of Word objects to save
     * @param output the File to save the data into
     * @throws FileNotFoundException  thrown if the File cannot be found
     */
    private static void saveFile(List<Word> list, File output)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(output)) {
            for (Word x : list){
                writer.println(x);
            }
        }
    }

    /**
     *A helper method that generates a report of the most frequent entries
     *  from the given sorted List. If topHits is greater than the total
     *  number of entries in the list, it will print out the entire list
     * @param list the List from which to generate the report
     * @param type a String describing what the contents of the list are
     *             (i.e. "Words", "Bigrams", etc)
     * @param topHits the number of items to display in the report
     */
    private static void report(List<Word> list,
                               String type,
                               int topHits){
        System.out.println("Top " + topHits + " " + type + " are:");
        if (topHits < list.size()){
            for (int i = 0; i < topHits; i++){
                String formattedString = String.format("%-6d", i+1);

                System.out.println(formattedString + list.get(i));
            }
        } else{
            for (int i = 0; i < list.size(); i++){
                String formattedString = String.format("%-6d", i+1);
                System.out.println(formattedString + list.get(i));
            }
        }

    }

    
}
