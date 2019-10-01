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

    /**
     * Public constructor of FieldCoord
     * @param row Referenced Row
     * @param col Referenced Column
     */   
    public FieldCoord(int row, int col)
    {
        this.setRow(row);
        this.setCol(col);
    }

    /**
     * @return the Row
     */
    public int getRow() {
        return mRow;
    }

    /**
     * @param mRow the Row to set
     */
    public void setRow(int mRow) {
        this.mRow = mRow;
    }

    /**
     * @return the Col
     */
    public int getCol() {
        return mCol;
    }

    /**
     * @param mCol the Col to set
     */
    public void setCol(int mCol) {
        this.mCol = mCol;
    }
    
}
