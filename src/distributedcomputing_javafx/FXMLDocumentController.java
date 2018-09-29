/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedcomputing_javafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author drew
 */
public class FXMLDocumentController implements Initializable {
    //FXML componenets (the name needs to be same as the fxid set in .fxml)
    @FXML
    private Canvas canvas;  //the canvas we can draw on
    @FXML
    private ColorPicker brush_color; //used to get color of brush
    @FXML
    private Slider brush_width; //used to get width of brush
    @FXML
    private Circle brush_circle;    //used to show the brush position/width
    
    private GraphicsContext gc; //used to draw on the canvas
    private double width = 30;  //the width of the brush
    private double width_half = width/2;   //used for brush offset when drawing
    
    private final double min_width = 1.0;
    private final double max_width = 100.0;
    private final double scroll_sensitivity = .1;
    
    @FXML
    private void handleCanvasPressed(MouseEvent event) {
        //get graphics context
        if(gc == null)
            gc = canvas.getGraphicsContext2D();
        gc.setFill(brush_color.getValue());
        gc.fillOval(event.getX() - width_half, event.getY() - width_half, width, width);
    }
    @FXML
    private void handleCanvasDrag(MouseEvent event) {
        brush_circle.setCenterX(event.getX());
        brush_circle.setCenterY(event.getY());
        gc.setFill(brush_color.getValue());
        gc.fillOval(event.getX() - width_half, event.getY() - width_half, width, width);
    }
    @FXML
    private void handleCanvasReleased(MouseEvent event) {
        gc.setFill(brush_color.getValue());
        gc.fillOval(event.getX() - width_half, event.getY() - width_half, width, width);
    }
    
    //move the brush cursor when mousing over the canvas
    @FXML
    private void handlerCanvasMouseOver(MouseEvent event) {
        brush_circle.setCenterX(event.getX());
        brush_circle.setCenterY(event.getY());
    }
    @FXML
    private void handlerCanvasMouseEnter(MouseEvent event) {
        brush_circle.setVisible(true);
    }
    @FXML
    private void handlerCanvasMouseExit(MouseEvent event) {
        brush_circle.setVisible(false);
    }
    
    //change brush radius when changing slider
    @FXML
    private void handlerGetBrushWidth(MouseEvent event) {
        width = brush_width.getValue();
        width_half = width/2;
        
        brush_circle.setRadius(width_half);
    }
    
    //change brush radius when scrolling
    @FXML
    private void handlerCanvasScroll(ScrollEvent event) {
        double curWidth = width;
        curWidth += event.getDeltaY() * scroll_sensitivity;
        if(curWidth > max_width)
            curWidth = max_width;
        else if(curWidth < min_width)
            curWidth = min_width;
        
        width = curWidth;
        width_half = width/2;
        brush_circle.setRadius(width_half);
        brush_width.setValue(width);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
