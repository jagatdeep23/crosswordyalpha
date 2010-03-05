package Model;

/**
 * Template class to hold a pair of objects, designed for returning two objects from one function
 *
 * @author Team Yalpha, specifically Jordan Hollinger
 * @version 1.0
 */
public class Pair <E, F>
{
    private E first;
    private F second;

    /**
     * Constructor taking the first and second object
     *
     * @param first the first object
     * @param second the second object
     */
    public Pair (E first, F second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first object
     *
     * @return the first object in the pair
     */
    public E getFirst ()
    {
        return first;
    }

    /**
     * Gets the second object
     *
     * @return the second object in the pair
     */
    public F getSecond ()
    {
        return second;
    }

    /**
     * Sets the first object
     *
     * @param first the new first object for the pair
     */
    public void setFirst (E first)
    {
        this.first = first;
    }

    /**
     * Sets the second object
     *
     * @param second the new second object for the pair
     */
    public void setSecond (F second)
    {
        this.second = second;
    }
}
