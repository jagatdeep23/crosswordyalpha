/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 * Stores a point with three coordinates.
 * @author Patrick
 */
public class Point3D extends Point {
    private int m_z = 0;

    /**
     * Constructor taking three coordinates
     * @param a first coordinate
     * @param b second coordinate
     * @param c third coordinate
     */
    Point3D(int a, int b, int c)
    {
        super(a,b);
        m_z = c;
    }

    /**
     * Gets the z coordinate
     * @return z coordinate
     */
    public int getZ()
    {
        return m_z;
    }

    /**
     * Sets the z coordinate
     * @param tZ z coordinate
     */
    public void setZ(int tZ)
    {
        m_z = tZ;
    }

    /**
     * Creates a clone of the current instance
     * @return clone of the current instance
     */
    @Override
    public Point3D clone()
    {
        Point3D temp = null;
        temp = (Point3D)super.clone();

        return temp;
    }

}
