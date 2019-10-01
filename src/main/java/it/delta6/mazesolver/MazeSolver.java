/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.delta6.mazesolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Utente
 */
public class MazeSolver {

    private FieldGrid mGrid;

    private FieldCoord mStart = null;
    private FieldCoord mFinish = null;

    private List<List<FieldCoord>> mSolutions;

    /// <summary>
    /// Public constructor, it gets the grid on which work on
    /// </summary>
    /// <param name="fieldgrid"></param>
    public MazeSolver(FieldGrid fieldgrid)
    {
        mGrid = fieldgrid;
        mSolutions = new ArrayList<List<FieldCoord>>();
    }

    /// <summary>
    /// Get the solutions in order of match
    /// </summary>
    /// <returns>The list containing the solutions</returns>
    public List<List<FieldCoord>> GetSolutions()
    {
        return mSolutions;
    }

    /// <summary>
    /// Gets the solutions in order of number of steps
    /// </summary>
    /// <returns>The list conaining the solutions</returns>
    public List<List<FieldCoord>> GetSortedSolutions()
    {
        List <List<FieldCoord>> s = new ArrayList<List<FieldCoord>>(mSolutions);
        //s.Sort(new CompareSolution());
        //s.Reverse();
        return s;
    }

    private FieldCoord FindFieldElement(FieldElement element)
    {
        for(int r = 0; r < mGrid.getRows(); r++)
        {
            for(int c = 0; c < mGrid.getCols(); c++)
            {
                if(mGrid.getCell(r, c) == element)
                {
                    return new FieldCoord(r, c);
                }
            }
        }

        return null;
    }

    public void solve()
    {
        mStart = FindFieldElement(FieldElement.Start);

        mFinish = FindFieldElement(FieldElement.Finish);

        if (mStart == null || mFinish == null) return;

        List<FieldCoord> path = new ArrayList<FieldCoord>();
        path.add(mStart);

        processNode(mStart, path);
    }

    private List<FieldCoord> getBorder(FieldCoord node)
    {
        List<FieldCoord> border = new ArrayList<FieldCoord>();

        // North
        if (node.getRow() - 1 >= 0)
        {
            FieldElement elem = mGrid.getCell(node.getRow() - 1, node.getCol());
            if (elem == FieldElement.Empty || elem == FieldElement.Finish)
            {
                border.add(new FieldCoord(node.getRow() - 1, node.getCol()));
            }
        }

        // South
        if (node.getRow() + 1 < mGrid.getRows())
        {
            FieldElement elem = mGrid.getCell(node.getRow() + 1, node.getCol());
            if (elem == FieldElement.Empty || elem == FieldElement.Finish)
            {
                border.add(new FieldCoord(node.getRow() + 1, node.getCol()));
            }
        }

        // West
        if (node.getCol() - 1 >= 0)
        {
            FieldElement elem = mGrid.getCell(node.getRow(), node.getCol() - 1);
            if (elem == FieldElement.Empty || elem == FieldElement.Finish)
            {
                border.add(new FieldCoord(node.getRow(), node.getCol() - 1));
            }
        }

        // East
        if (node.getCol() + 1 < mGrid.getCols())
        {
            FieldElement elem = mGrid.getCell(node.getRow(), node.getCol() + 1);
            if (elem == FieldElement.Empty || elem == FieldElement.Finish)
            {
                border.add(new FieldCoord(node.getRow(), node.getCol() + 1));
            }
        }

        return border;
    }

    public void processNode(FieldCoord node, List<FieldCoord> path)
    {
        List<FieldCoord> border = getBorder(node);

        for(FieldCoord newnode:border)
        {
            boolean present = false;
            for(FieldCoord fc:path)
            {
                if(fc.getRow() == newnode.getRow() && fc.getCol() == newnode.getCol())
                {                        
                    present = true;
                    break;
                }
            }

            if(!present)
            {

                List<FieldCoord> newpath = new ArrayList<FieldCoord>(path);

                newpath.add(newnode);
                if (mGrid.getCell(newnode.getRow(), newnode.getCol()) == FieldElement.Finish)
                {
                    mSolutions.add(newpath);
                }
                else
                {
                    processNode(newnode, newpath);
                }
            }
        }    
    }
}
