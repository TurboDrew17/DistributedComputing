/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedcomputing_javafx;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** used for sockets **/
import java.net.*;
import java.io.*;

/**
 *
 * @author drew
 */
public class Main_Router extends Application {
    private Parent root;
    private Scene scene;
    
    public static Object[][] routing_table = new Object[10][3]; //name, address, socket
    static int table_index = 0;
    public static ServerSocket serverSocket = null;
    
    public static boolean running = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        //root = FXMLLoader.load(getClass().getResource("Client.fxml"));
        root = FXMLLoader.load(getClass().getResource("Router.fxml"));
        
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
        launch(args);
    }
    
    //open my IP and port to get ready for others to connect
    public static void SetMyIP(String address, String port) {
        try {
          serverSocket = new ServerSocket(Integer.parseInt(port));
          System.out.println("ServerRouter is Listening on port: " + port + ".");
        }
        catch (IOException e) {
          System.err.println("Could not listen on port: " + port + ".");
          System.exit(1);
        }
    }
    
    public static boolean IsInTable(String ip) {
        for(int i = 0; i < routing_table.length; i++) {
            if(routing_table[i][1] == ip) {
                return true;
            }
        }
        return false;
    }
    
    //keep track of any sockets making connections to router
    public static void AddToTable(Socket s) {
        //if already in table, don't add
        if(IsInTable(s.getInetAddress().getHostAddress()))
            return;
        routing_table[table_index][0] = "Address " + table_index;
        routing_table[table_index][1] = s.getInetAddress().getHostAddress();
        routing_table[table_index][2] = s;
        
        //display in app
        RouterController.SetTable(table_index, (String)routing_table[table_index][0], (String)routing_table[table_index][1], "1234");
        
        table_index++;
    }
    
    //keep track of any sockets making connections to router
    public static void AddToTable(AddressObject ao) {
        //if already in table, don't add
        if(IsInTable(ao.address))
            return;
        routing_table[table_index][0] = ao.name;
        routing_table[table_index][1] = ao.address;
        routing_table[table_index][2] = ao.port;
        
        //display in app
        RouterController.SetTable(table_index, (String)routing_table[table_index][0], (String)routing_table[table_index][1], "1234");

        table_index++;
    }
    
    public static void StartListening() {
        try {
            running = true;
            Thread_Listening tl = new Thread_Listening();
            tl.start();
            System.out.println("Starting to listen");
        }
        catch(Exception e) {
            System.out.println("FAILED TO CREATE THREAD TO LISTEN ON SERVER SOCKET");
        }
//        running = true;
//        Socket clientSocket; //represents a new client that connects
//        while(running) {
//            try {
//                clientSocket = serverSocket.accept();
//                AddToTable(clientSocket);
//                //make new thread to handle socket
//                Thread_Client st = new Thread_Client(clientSocket);
//            }
//            catch (IOException e) {
//                
//            }
//        }
    }
}