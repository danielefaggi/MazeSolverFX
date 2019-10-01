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

    /**
     * Public constructor, it gets the grid on which work on
     * @param fieldgrid the grid on which work on
     */
    public MazeSolver(FieldGrid fieldgrid)
    {
        mGrid = fieldgrid;
        mSolutions = new ArrayList<List<FieldCoord>>();
    }

    /**
     * Get the solutions in order of match
     * @return The list containing the solutions
     */
    public List<List<FieldCoord>> GetSolutions()
    {
        return mSolutions;
    }

    /**
     * Gets the solutions in order of number of steps
     * @return The list conaining the solutions
     */
    public List<List<FieldCoord>> GetSortedSolutions()
    {
        List <List<FieldCoord>> s = new ArrayList<List<FieldCoord>>(mSolutions);
        //s.Sort(new CompareSolution());
        //s.Reverse();
        return s;
    }

    /**
     * Find an element in the grid and returns its coordinates
     * @param element the element to be found
     * @return the FieldCoord of the element
     */
    private FieldCoord findFieldElement(FieldElement element)
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

    /**
     * Start the solution of the maze
     */
    public void solve()
    {
        mStart = findFieldElement(FieldElement.Start);

        mFinish = findFieldElement(FieldElement.Finish);

        if (mStart == null || mFinish == null) return;

        List<FieldCoord> path = new ArrayList<FieldCoord>();
        path.add(mStart);

        processNode(mStart, path);
    }

    /**
     * Get the cells of the border of the current cell
     * @param node the cell owning the border
     * @return the FieldCoord list of the cell of the border
     */
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

    /**
     * Process a cell (or node)
     * @param node the current cell
     * @param path the path travelled so far
     */
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
