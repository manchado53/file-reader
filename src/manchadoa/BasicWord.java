/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Adrian Manchado
 */
package manchadoa;
/**
 * A basic word object storing the word and its location
 */
public class BasicWord extends Word {
    private final long location;
    /**
     *Constructor for a BasicWord
     *@param word the word to store
     * @param location the location of the current word
     */
    public BasicWord(String word, long location){
        super(word);
        this.location = location;
    }

    /**
     * This method is required by the superclass, but a BasicWord only has a single,
     * final location. The method must be implemented, but throw an
     * UnsupportedOperationException when called
     * @param location the location of the word
     * @throws UnsupportedOperationException thrown when the method is called
     */
    @Override
    public void addLocation(long location) throws UnsupportedOperationException {
        System.out.println("You can not add a location to a existing word");
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a String representation of the Word. The method is made abstract
     * so all inheriting classes must implement their own toString() method and are
     * not allowed to inherit the Object class toString()
     * @return a String representation of the Word
     */
    @Override
    public String toString() {
        return this.word;
    }

    /**
     * Compares another Object to check if that Object is equal to this BasicWord.
     * Equality is measured by whether the other Object is also a BasicWord and the word
     * contained in this BasicWord matches exactly the word contained in the other
     * BasicWord object.
     * @param o the Object to compare to this
     * @return true if o is both a BasicWord and contains the same word as this
     * BasicWord, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof BasicWord && ((BasicWord) o).word.equals(this.word);
    }

    /**
     * Compares this object with the specified object for order. Returns a negative
     * integer, zero, or a positive integer as this object is less than, equal to,
     * or greater than the specified object.For this class, we are comparing the
     * words stored in the two Word objects
     * @param that the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Word that) {
        return this.word.compareTo(that.word);
    }

    public long getLocation() {
        return location;
    }
}
