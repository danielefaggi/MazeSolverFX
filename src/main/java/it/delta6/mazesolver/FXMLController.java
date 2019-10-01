package it.delta6.mazesolver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
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
    
    private FieldGrid mFieldGrid;
    
    private int mSquareSizeX = 10;
    private int mSquareSizeY = 10;
    
    private int mRows;
    private int mCols;
    
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

        for(int r = 0;r < mRows+1;r++) {
            for(int c = 0;c < mCols+1; c++) {
                if(mFieldGrid.getCell(r, c) == FieldElement.Wall) {
                    Rectangle rect = new Rectangle(mSquareSizeX, mSquareSizeY);
                    rect.setX(c * mSquareSizeX);
                    rect.setY(r * mSquareSizeY);
                    rect.setFill(Color.GRAY);
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
        
        int col = (int) event.getX() / mSquareSizeX;
        int row = (int) event.getY() / mSquareSizeY;
                
        if(mFieldGrid.getCell(row, col) == FieldElement.Wall) {
            mFieldGrid.SetCell(row, col, FieldElement.Empty);
        } else {
            mFieldGrid.SetCell(row, col, FieldElement.Wall);        
        }
        
        updateGrid();
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

}
