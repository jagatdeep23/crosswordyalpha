/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 * Stores data for an intersection between two words: the index of word B in a wordmap, the index of intersection in word one,
 * and index of intersection in word two
 * @author Team Yalpha, specifically Jordan Hollinger and Patrick Martin
 */
public class WordIntersection
{
    private int m_wordIndex = 0;
    private int m_wordAPosition = 0;
    private int m_wordBPosition = 0;

    /**
     * Constructor taking three coordinates
     * @param a first coordinate
     * @param b second coordinate
     * @param c third coordinate
     */
    WordIntersection(int wordIndex, int wordAPos, int wordBPos)
    {
        m_wordIndex = wordIndex;
        m_wordAPosition = wordAPos;
        m_wordBPosition = wordBPos;
    }

    /**
     * Gets the index of the matching word in its containing WordMap
     * @return the index of the matching word in its containing WordMap
     */
    public int getWordIndex()
    {
        return m_wordIndex;
    }

    /**
     * Sets the index of the matching word in its containing WordMap
     * @param index the index of the matching word in its containing WordMap
     */
    public void setWordIndex(int index)
    {
        m_wordIndex = index;
    }

    /**
     * Gets the index of the matching letter in Word A
     * @return the index of the matching letter in Word A
     */
    public int getWordAPosition()
    {
        return m_wordAPosition;
    }

    /**
     * Sets the index of the matching letter in Word A
     * @param pos the index of the matching letter in Word A
     */
    public void setWordAPosition(int pos)
    {
        m_wordAPosition = pos;
    }

    /**
     * Gets the index of the matching letter in Word B
     * @return the index of the matching letter in Word B
     */
    public int getWordBPosition()
    {
        return m_wordBPosition;
    }

    /**
     * Sets the index of the matching letter in Word B
     * @param pos the index of the matching letter in Word B
     */
    public void setWordBPosition(int pos)
    {
        m_wordBPosition = pos;
    }

    /**
     * Creates a clone of the current instance
     * @return clone of the current instance
     */
    @Override
    public WordIntersection clone()
    {
        WordIntersection temp = null;
        temp.m_wordIndex = this.m_wordIndex;
        temp.m_wordAPosition = this.m_wordAPosition;
        temp.m_wordBPosition = this.m_wordBPosition;

        return temp;
    }

}
