package it.delta6.mazesolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class FXMLController implements Initializable {
    
    @FXML
    private Pane panRoot;
    @FXML
    private Pane panGrid;
    @FXML
    private Spinner<Integer> spinRows;
    @FXML
    private Spinner<Integer> spinCols;
    @FXML
    private ToggleButton togWall;
    @FXML
    private ToggleButton togStart;
    @FXML
    private ToggleButton togFinish;
    @FXML
    private Button butSolve;
    @FXML
    private Button butClear;
    @FXML
    private Button butLoad;
    @FXML
    private Button butSave;
    
    private FieldElement mPaintElement = null;
    
    private FieldGrid mFieldGrid;
    
    private int mSquareSizeX = 10;
    private int mSquareSizeY = 10;
    
    private int mRows;
    private int mCols;
    
    // The solver is attached here
    private MazeSolver mSolver = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        mRows = 10;
        mCols = 10;

        mFieldGrid = new FieldGrid(mCols, mRows);

        spinRows.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, mRows));
        spinCols.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, mCols));
        
        spinRows.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                spinRows_Changed();
            }
        });
        spinCols.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                spinCols_Changed();
            }
        });
        
        updateGrid();
    }    
            
    private void updateGrid()
    {
        
        if (mFieldGrid.getCols() != mCols) mFieldGrid.setCols(mCols);
        if (mFieldGrid.getRows() != mRows) mFieldGrid.setRows(mRows);
        
        int sizex = mCols * mSquareSizeX;
        int sizey = mRows * mSquareSizeY;
        
        panGrid.setPrefSize((double)sizex, (double) sizey);
        
        panGrid.getChildren().clear();

        if(mSolver != null) {
            if(mSolver.GetSolutions().toArray().length > 0) {
                for(FieldCoord fc:mSolver.GetSolutions().get(0)) {
                                        
                    Rectangle rect = new Rectangle(mSquareSizeX, mSquareSizeY);
                    rect.setX(fc.getCol() * mSquareSizeX);
                    rect.setY(fc.getRow() * mSquareSizeY);
                    rect.setFill(Color.YELLOW);
                    rect.setMouseTransparent(true);
                    panGrid.getChildren().add(rect);
                    
                            
                }
            }
        }

        for(int r = 0;r < mRows+1;r++) {
            for(int c = 0;c < mCols+1; c++) {
                if(mFieldGrid.getCell(r, c) == FieldElement.Wall) {
                    Rectangle rect = new Rectangle(mSquareSizeX, mSquareSizeY);
                    rect.setX(c * mSquareSizeX);
                    rect.setY(r * mSquareSizeY);
                    rect.setFill(Color.GRAY);
                    rect.setMouseTransparent(true);
                    panGrid.getChildren().add(rect);
                } else if(mFieldGrid.getCell(r, c) == FieldElement.Start) {
                    Rectangle rect = new Rectangle(mSquareSizeX, mSquareSizeY);
                    rect.setX(c * mSquareSizeX);
                    rect.setY(r * mSquareSizeY);
                    rect.setFill(Color.GREEN);
                    rect.setMouseTransparent(true);
                    panGrid.getChildren().add(rect);                    
                } else if(mFieldGrid.getCell(r, c) == FieldElement.Finish) {
                    Rectangle rect = new Rectangle(mSquareSizeX, mSquareSizeY);
                    rect.setX(c * mSquareSizeX);
                    rect.setY(r * mSquareSizeY);
                    rect.setFill(Color.RED);
                    rect.setMouseTransparent(true);
                    panGrid.getChildren().add(rect);
                }            
            }
        }
        
        for(int r = 0;r < mRows+1;r++){
            Line line = new Line();
            line.setStartX(0.0);
            line.setStartY((double)r * mSquareSizeY);
            line.setEndX((double)sizex);
            line.setEndY((double) r * mSquareSizeY);
            panGrid.getChildren().add(line);
        }
        
        for(int c = 0;c < mCols+1; c++){
            Line line = new Line();
            line.setStartX((double)c * mSquareSizeX);
            line.setStartY(0.0);
            line.setEndX((double)c * mSquareSizeX);
            line.setEndY((double)sizey);
            panGrid.getChildren().add(line);
        }
                
    }   
    
    @FXML
    private void spinRows_Changed() {
        
        mRows = spinRows.getValue();
        updateGrid();
    }
    
    @FXML
    private void spinCols_Changed() {
        
        mCols = spinCols.getValue();
        updateGrid();
    }
    
    @FXML
    private void panGrid_MousePressed(MouseEvent event) {
        
        if(event.isPrimaryButtonDown()) {
            int col = (int) event.getX() / mSquareSizeX;
            int row = (int) event.getY() / mSquareSizeY;

            if(togWall.isSelected()) {
                if(mFieldGrid.getCell(row, col) == FieldElement.Wall) {
                    mFieldGrid.setCell(row, col, FieldElement.Empty);
                    mPaintElement = FieldElement.Empty;
                } else {
                    mFieldGrid.setCell(row, col, FieldElement.Wall);        
                    mPaintElement = FieldElement.Wall;
                }
            } else if(togStart.isSelected()) {
                mFieldGrid.ClearCells(FieldElement.Start);
                mFieldGrid.setCell(row, col, FieldElement.Start);
                mPaintElement = null;
            } else if(togFinish.isSelected()) {
                mFieldGrid.ClearCells(FieldElement.Finish);
                mFieldGrid.setCell(row, col, FieldElement.Finish);                                
                mPaintElement = null;
            }

            updateGrid();
        }
    }
        
    @FXML
    private void panGrid_MouseDragged(MouseEvent event) {
        
        if(event.isPrimaryButtonDown()) {
            
            if(mPaintElement != null) {
                int col = (int) event.getX() / mSquareSizeX;
                int row = (int) event.getY() / mSquareSizeY;
                mFieldGrid.setCell(row, col, mPaintElement);
                updateGrid();
            }
        } else {
            mPaintElement = null;
        }
        
    }
    
    @FXML
    private void panGrid_MouseReleased(MouseEvent event) {
        
        mPaintElement = null;
    }
    
    @FXML
    private void togWall_Action(Event args) {
        if(togWall.isSelected()) {
            togStart.setSelected(false);
            togFinish.setSelected(false);
        } else {
            togWall.setSelected(true);
        }
    }
    
    @FXML
    private void togStart_Action(Event args) {
        if(togStart.isSelected()) {
            togWall.setSelected(false);
            togFinish.setSelected(false);
        } else {
            togStart.setSelected(true);
        }       
    }

    @FXML
    private void togFinish_Action(Event args) {
        if(togFinish.isSelected()) {
            togWall.setSelected(false);
            togStart.setSelected(false);   
        } else {
            togFinish.setSelected(true);
        }        
    }

    @FXML
    private void butSolve_Action(Event args) {
        
        mSolver = new MazeSolver(mFieldGrid);

        mSolver.solve();
        
        updateGrid();
        
    }
    
    @FXML
    private void butClear_Action(Event args) {
     
        mSolver = null;
        
        updateGrid();
    }
    
    @FXML
    private void butSave_Action(Event args) {
        
        FileChooser fileChooser = new FileChooser();
 
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze");
        fileChooser.setTitle("Save maze definition file");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(panRoot.getScene().getWindow());

        if(file != null) {
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println("Maze 1.0");
                writer.println(mFieldGrid.getRows());
                writer.println(mFieldGrid.getCols());
                
                int i = 1;
                for (int r = 0; r < mFieldGrid.getRows(); r++)
                {
                    for (int c = 0; c < mFieldGrid.getCols(); c++)
                    {
                        byte val = 0;
                        FieldElement elem = mFieldGrid.getCell(r, c);
                        if (elem == FieldElement.Wall)
                        {
                            val = 1;
                        }
                        else if (elem == FieldElement.Start)
                        {
                            val = 2;
                        }
                        else if (elem == FieldElement.Finish)
                        {
                            val = 3;
                        }
                        writer.print(val);
                        if (++i >= 40)
                        {
                            i = 1;
                            writer.println();
                        }
                    }
                }
                
                writer.close();
                
            } catch (IOException ex) {
            }        
        }
    }
    
    @FXML
    private void butLoad_Action(Event args) {

        FileChooser fileChooser = new FileChooser();
 
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze");
        fileChooser.setTitle("Load maze definition file");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showOpenDialog(panRoot.getScene().getWindow());

        if(file != null) {
            try {
                
                BufferedReader reader = new BufferedReader(new FileReader(file));
                
                String line;

                line = reader.readLine();
                if (!line.equals("Maze 1.0"))
                {
                    reader.close();
                    return;
                }

                int rows = 0;
                line = reader.readLine();
                try {
                    rows = Integer.parseInt(line);
                } catch(NumberFormatException ex) {
                    reader.close();
                    return;
                }

                int cols = 0;
                line = reader.readLine();
                try {
                    cols = Integer.parseInt(line);
                } catch(NumberFormatException ex) {
                    reader.close();
                    return;
                }

                mFieldGrid.setRows(rows);
                mFieldGrid.setCols(cols);

                mRows = rows;
                mCols = cols;
                
                spinRows.getValueFactory().setValue(mRows);
                spinCols.getValueFactory().setValue(mCols);

                updateGrid();

                line = reader.readLine();
                int i = 0;
                for (int r = 0; r < mFieldGrid.getRows(); r++)
                {
                    for (int c = 0; c < mFieldGrid.getCols(); c++)
                    {
                        byte val = 0;
                        try {
                            val = Byte.parseByte(line.substring(i, i + 1));
                        } catch(NumberFormatException ex) {
                            
                        }
                        
                        if(val == 1)
                        {
                            mFieldGrid.setCell(r, c, FieldElement.Wall);
                        }
                        else if(val == 2)
                        {
                            mFieldGrid.setCell(r, c, FieldElement.Start);
                        }
                        else if(val == 3)
                        {
                            mFieldGrid.setCell(r, c, FieldElement.Finish);
                        }
                        else
                        {
                            mFieldGrid.setCell(r, c, FieldElement.Empty);
                        }

                        if(++i >= line.length())
                        {
                            line = reader.readLine();
                            i = 0;
                        }
                    }
                }
                
                reader.close();
                
                updateGrid();
                
            } catch(IOException ex) {
                
            }
        }
    }

}
