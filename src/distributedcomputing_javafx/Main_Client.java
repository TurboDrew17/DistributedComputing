/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedcomputing_javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author drew
 */
public class Main_Client extends Application {
    private Parent root;
    private Scene scene;
    
    //String routerName = "j263-08.cse1.spsu.edu"; // ServerRouter host name
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        //root = FXMLLoader.load(getClass().getResource("Client.fxml"));
        root = FXMLLoader.load(getClass().getResource("Client.fxml"));
        
        //Show the UI
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Distributed Computing JavaFX");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch the JavaFX applicaiton
        launch(args);
        
        
    }
    
    public static void ConnectToRouter(String routerAddress, String myPort, String destination) throws IOException {
        ClientController.ShowWaitingOnRouter();    //connected to router, now need to connect to destination
        
        Thread_Client tc = new Thread_Client(myPort, routerAddress, destination);
        tc.start();
    }
}
