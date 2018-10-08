/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedcomputing_javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author drew
 */
public class ClientController implements Initializable {
    
    static ClientController instance;
    
    //waiting to get response from router
    public static void ShowWaitingOnRouter() {
        instance.canvas_start.setVisible(false);
        instance.canvas_waiting_router.setVisible(true);
    }
    
    //called when successfully connect to router and trying to connect to destination
    public static void ShowWaitingOnDestination() {
        instance.canvas_waiting_router.setVisible(false);
        instance.canvas_waiting_destination.setVisible(true);
    }
    
    //called when connected to destination
    public static void ShowCanvas() {
        instance.canvas_waiting_destination.setVisible(false);
        instance.canvas_connected.setVisible(true);
    }
    
    //called when we receive brush from other client
    public static void ReceiveStroke(String data) {
        try {
            String[] split = data.split(";");
            //get the color of stroke sent
            double r = Double.parseDouble(split[0]);
            double g = Double.parseDouble(split[1]);
            double b = Double.parseDouble(split[2]);
            double a = Double.parseDouble(split[3]);

            //get width
            double width = Double.parseDouble(split[4]);
            double width_half = width/2;
            ArrayList<Position> positions = new ArrayList<>();

            //iterate through rest of line and add the positions found
            for(int i = 5; i+1 < split.length; i++) {
                positions.add(new Position(Double.parseDouble(split[i]), Double.parseDouble(split[i+1])));
            }

            //prepare to draw on screen
            if(instance.gc == null)
                instance.gc = instance.canvas.getGraphicsContext2D();
            instance.gc.setFill(new Color(r,g,b,a));    //set color
            //iterate and draw for each position we received
            for(Position p: positions) {
                instance.gc.fillOval(p.getX() - width_half, p.getY() - width_half, width, width);
            }
        }
        catch(Exception e) {
            System.out.println("Failed to draw stroke... " + e.getMessage());
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //BEFORE CONNECT
    
    @FXML TextField text_port, text_router, text_destination;
    @FXML private void HandleConnectPressed(ActionEvent event) {
        try {
            Main_Client.ConnectToRouter(text_router.getText(), text_port.getText(), text_destination.getText());
        }
        catch(Exception e) {
            System.out.println("Error connecting to router... " + e.getMessage());
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CONNECTED
    
    //FXML componenets (the name needs to be same as the fxid set in .fxml)
    @FXML private Canvas canvas;  //the canvas we can draw on
    @FXML private ColorPicker brush_color; //used to get color of brush
    @FXML private Slider brush_width; //used to get width of brush
    @FXML private Circle brush_circle;    //used to show the brush position/width
    
    //Show/hide these components
    @FXML AnchorPane canvas_start, canvas_waiting_router, canvas_waiting_destination;
    @FXML SplitPane canvas_connected;
    
    private GraphicsContext gc; //used to draw on the canvas
    private double width = 30;  //the width of the brush
    private double width_half = width/2;   //used for brush offset when drawing
    
    private final double min_width = 1.0;
    private final double max_width = 100.0;
    private final double scroll_sensitivity = .1;
    
    private ArrayList<Position> stroke_positions;
    
    private String GetStrokeString() {
        String result = "";
        Color c = brush_color.getValue();
        result += c.getRed() + ",";
        result += c.getGreen() + ",";
        result += c.getBlue() + ",";
        result += c.getOpacity() + ",";
        
        result += width;
        
        for(Position p: stroke_positions) {
            result += "," + p.getX();
            result += "," + p.getY();
        }
        
        return result;
    }
    
    @FXML private void handleCanvasPressed(MouseEvent event) {
        //clear the array list
        stroke_positions = new ArrayList<>();
        stroke_positions.add(new Position(event.getX(), event.getY()));
        
        //get graphics context
        if(gc == null)
            gc = canvas.getGraphicsContext2D();
        gc.setFill(brush_color.getValue());
        gc.fillOval(event.getX() - width_half, event.getY() - width_half, width, width);
    }
    @FXML private void handleCanvasDrag(MouseEvent event) {
        stroke_positions.add(new Position(event.getX(), event.getY()));
        
        brush_circle.setCenterX(event.getX());
        brush_circle.setCenterY(event.getY());
        gc.setFill(brush_color.getValue());
        gc.fillOval(event.getX() - width_half, event.getY() - width_half, width, width);
    }
    @FXML private void handleCanvasReleased(MouseEvent event) {
        stroke_positions.add(new Position(event.getX(), event.getY()));
        System.out.println(GetStrokeString());
        gc.setFill(brush_color.getValue());
        gc.fillOval(event.getX() - width_half, event.getY() - width_half, width, width);
    }
    
    //move the brush cursor when mousing over the canvas
    @FXML private void handlerCanvasMouseOver(MouseEvent event) {
        brush_circle.setCenterX(event.getX());
        brush_circle.setCenterY(event.getY());
    }
    @FXML private void handlerCanvasMouseEnter(MouseEvent event) {
        brush_circle.setVisible(true);
    }
    @FXML private void handlerCanvasMouseExit(MouseEvent event) {
        brush_circle.setVisible(false);
    }
    
    //change brush radius when changing slider
    @FXML private void handlerGetBrushWidth(MouseEvent event) {
        width = brush_width.getValue();
        width_half = width/2;
        
        brush_circle.setRadius(width_half);
    }
    
    //change brush radius when scrolling
    @FXML private void handlerCanvasScroll(ScrollEvent event) {
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
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
    }
}