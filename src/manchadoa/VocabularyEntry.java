/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Adrian Manchado
 */
package manchadoa;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains information about a single word, it's location(s),
 * and its occurrences.
 */
public class VocabularyEntry extends Word {
    private int occurrences;
    private final List<Long> locations;

    /**
     * Constructor for a Word object
     * @param basic Word to add to the list
     */
    public VocabularyEntry(BasicWord basic){
        super(basic.word);
        locations = new ArrayList<>();
        locations.add(basic.getLocation());
        occurrences = 1;
    }
    public int getOccurrences(){
        return this.occurrences;
    }

    public List<Long> getLocations() {
        return this.locations;
    }

    /**
     * Adds a new locations to an existing Word and increments the number
     * of occurrences. If the location already has been added, or the
     * location is not a valid location (i.e. negative), an exception
     * is thrown.
     * @param location the location of the word
     * @throws IllegalArgumentException - thrown if the location already
     * exists or is invalid
     */
    @Override
    public void addLocation(long location) throws IllegalArgumentException{
        if (location == locations.indexOf(location) || location < 0){
            throw new IllegalArgumentException();
        } else {
            locations.add(location);
            occurrences++;
        }
    }

    /**
     * Generates a String representation of the VocabularyEntry that contains
     * both the word of the entry and the number of occurrences of the entry.
     * Example:penguin      47
     * @return a String representation of the VocabularyEntry
     */
    @Override
    public String toString() {
        return String.format("%-15s %4d",
                this.word, this.occurrences);
    }
    /**
     * Compares another Object to check if that Object is equal to this
     * VocabularyEntry. Equality is measured by whether the other Object
     * is also a VocabularyEntry and the word contained in this VocabularyEntry
     * matches exactly the word contained in the other VocabularyEntry object.
     * @param o the Object to compare to this
     * @return true if o is both a VocabularyEntry and contains the same word as
     * this VocabularyEntry, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof VocabularyEntry &&
                ((VocabularyEntry) o).word.equals(this.word);
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * For this class, we are comparing the number of occurrences of the two
     * VocabularyEntry objects.
     * @param that the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.\
     */
    @Override
    public int compareTo(Word that) {
        if (that instanceof VocabularyEntry) {
            if (((VocabularyEntry) that).occurrences < occurrences) {
                return 1;
            } else if (((VocabularyEntry) that).occurrences > occurrences) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }
}
