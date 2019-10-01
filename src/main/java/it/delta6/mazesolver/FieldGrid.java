/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.delta6.mazesolver;

/**
 *
 * @author Utente
 */
public class FieldGrid {
    
    private int mCols;
    private int mRows;
    private FieldElement[][] mGrid;

    /**
     * Public constructor
     * 
     * @param cols Wanted columns
     * @param rows Wanted rows
     */

     public FieldGrid(int cols, int rows) {
        mCols = cols;
        mRows = rows;
        mGrid = null;
        update();
    }

        /// <summary>
        /// Number of columns
        /// </summary>
    public int getCols() {
        return mCols;
    }

    public void setCols(int cols) {        
        mCols = cols;
        update();
    }

    
    public int getRows() {
        
        return mRows;
        
    }
    
    public void setRows(int rows) {
        
        mRows = rows;
        update();
    }

    /**
     * Set the cell element type
     * @param row Row coordinate
     * @param col Column coordinate
     * @param element Element to be assigned to cell
     */    
    public void SetCell(int row, int col, FieldElement element) {
        if (row < mGrid.length && col < mGrid[0].length && row >= 0 && col >= 0) {
                mGrid[row][col] = element;
        }

    }

    /**
     * Get the cell element type
     * @param row Row coordinate
     * @param col Column coordinate
     * @return The element contained in the cell grid
     */
    public FieldElement getCell(int row, int col) {
        if (row < mGrid.length && col < mGrid[0].length && row >= 0 && col >= 0) {
                return mGrid[row][col];
        } else {
            return FieldElement.Empty;
        }
    }
    
    /**
     * Clear the cells of the grid from a specific element
     * @param element The element to be cleared
     */

    public void ClearCells(FieldElement element)
    {
        for(int r = 0; r < mGrid.length; r++) {
            for(int c = 0; c < mGrid[0].length; c++) {
                if(mGrid[r][c] == element) {
                    mGrid[r][c] = FieldElement.Empty;
                }
            }
        }
    }

    /**
     * Initialize the grid elements
     */
    private static void InitGrid(FieldElement[][] grid)
    {
        for (int r = 0; r < grid.length; r++) {
            for(int c = 0; c < grid[0].length; c++) {
                grid[r][c] = FieldElement.Empty;
            }
        }
    }

    /**
     * Update the grid starting from the selected numeber of Rows and Columns
     */
    
    private void update()
    {
        FieldElement[][] newgrid = new FieldElement[mRows][mCols];

        InitGrid(newgrid);

        if(mGrid == null) {
            
            mGrid = newgrid;

            return;
        }

        for(int r = 0; r<mRows; r++) {
            for(int c = 0; c < mCols; c++) {
                if(r < mGrid.length) {
                    if(c < mGrid[0].length) {
                        newgrid[r][c] = mGrid[r][c];
                    }
                }
            }
        }

        mGrid = newgrid;
    }

}
