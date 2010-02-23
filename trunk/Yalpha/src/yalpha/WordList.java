/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package yalpha;
import java.util.*;
/**
 *
 * @author Patrick
 */
public class WordList {
    private ArrayList<String> m_list = null;
    public int hell()
    {
        return 0;
    }

    public void set(WordList temp)
    {
        Object tempList = temp.m_list.clone();
        m_list = (ArrayList<String>)tempList;
    }

}
