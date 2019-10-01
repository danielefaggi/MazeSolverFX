/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.delta6.mazesolver;

/**
 *
 * @author Daniele
 */
public class FieldCoord {
        
    private int mRow;
    private int mCol;

       
    public FieldCoord(int row, int col)
    {
        this.setRow(row);
        this.setCol(col);
    }

    /**
     * @return the mRow
     */
    public int getRow() {
        return mRow;
    }

    /**
     * @param mRow the mRow to set
     */
    public void setRow(int mRow) {
        this.mRow = mRow;
    }

    /**
     * @return the mCol
     */
    public int getCol() {
        return mCol;
    }

    /**
     * @param mCol the mCol to set
     */
    public void setCol(int mCol) {
        this.mCol = mCol;
    }
    
}
