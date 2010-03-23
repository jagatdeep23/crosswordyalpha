/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author Patrick
 */
public class Point
    {
        private int x,y;

        Point(int tx, int ty)
        {
            x = tx;
            y = ty;
        }
        Point()
        {
            x =0;
            y =0;
        }

        void setX(int temp)
        {
            x = temp;
        }

        void setY(int temp)
        {
            y = temp;
        }

        int getX()
        {
            return x;
        }

        int getY()
        {
            return y;
        }

        void set(Point tempP)
        {
            x = tempP.getX();
            y = tempP.getY();
        }
    }
