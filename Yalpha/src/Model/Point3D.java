/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author Patrick
 */
public class Point3D extends Point {
    private int m_z = 0;

    Point3D(int a, int b, int c)
    {
        super(a,b);
        m_z = c;
    }

    public int getZ()
    {
        return m_z;
    }

    public void setZ(int tZ)
    {
        m_z = tZ;
    }

    @Override
    public Point3D clone()
    {
        Point3D temp = null;
        temp = (Point3D)super.clone();

        return temp;
    }

}
