/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Adrian Manchado
 */
package manchadoa;
/**
 * Abstract class that defines the basis of word objects
 */
public abstract class Word implements Comparable<Word> {
    protected final String word;
    /**
    *Constructor for the Word class that stores the inherited word
    *@param word the word of the text
    */
    public Word(String word){
        this.word = word;
    }
    /**
     * Adds a new location to an existing Word. If the location is
     * invalid an appropriate exception should be thrown
     *@param location the location of the word
     */
    public abstract void addLocation(long location);
    /**
     * Returns a String representation of the Word. The method is made abstract so
     * all inheriting classes must implement their own toString() method and are
     * not allowed to inherit the Object class toString()
     * @return a String representation of the Word
     */
    public abstract String toString();
    /**
     * Returns true if the passed in Object is equal to this Object The method is
     * made abstract so all inheriting classes must implement their own equals()
     * method and are not allowed to inherit the Object class equals()
     * @param o the Object to compare to this
     * @return true if this and o are equal, false otherwise
     */
    public abstract boolean equals(Object o);

}
