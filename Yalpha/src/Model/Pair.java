/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author jordan
 */
public class Pair <E, F>
{
    private E first;
    private F second;

    public Pair (E first, F second)
    {
        this.first = first;
        this.second = second;
    }

    public E getFirst ()
    {
        return first;
    }

    public F getSecond ()
    {
        return second;
    }

    public void setFirst (E first)
    {
        this.first = first;
    }

    public void setSecond (F second)
    {
        this.second = second;
    }
}
