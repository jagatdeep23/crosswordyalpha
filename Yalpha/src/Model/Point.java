/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 * Stores a point with two coordinates
 * @author Patrick
 */
public class Point implements Cloneable
    {
        private int x,y;

        /**
         * Constructor taking x and y coordinates
         * @param tx x coordinate
         * @param ty y coordinate
         */
        Point(int tx, int ty)
        {
            x = tx;
            y = ty;
        }
        /**
         * Default constructor
         * Initializes to (0, 0).
         */
        Point()
        {
            x =0;
            y =0;
        }

        /**
         * Sets the x coordinate
         * @param temp x coordinate
         */
        void setX(int temp)
        {
            x = temp;
        }
        /**
         *
         * Sets the y coordinate
         * @param temp y coordinate
         */
        void setY(int temp)
        {
            y = temp;
        }

        /**
         * Gets the x coordinate
         * @return x coordinate
         */
        int getX()
        {
            return x;
        }

        /**
         * Gets the y coordinate
         * @return y coordinate
         */
        int getY()
        {
            return y;
        }

        /**
         * Sets the x and y coordinates to that of the given point
         * @param tempP the point to set to
         */
        void set(Point tempP)
        {
            x = tempP.getX();
            y = tempP.getY();
        }

        /**
         * Creates a clone of the current instance
         * @return clone of the current instance
         */
        @Override
        public Point clone()
        {
            Point temp = null;
            try
            {
                temp = (Point)super.clone();
            }
            catch(CloneNotSupportedException e)
            {
                throw new RuntimeException("Clone Not supported");
            }
                return temp;
        }
    }
