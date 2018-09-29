/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedcomputing_javafx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author asavas
 */
public class MessageTesting {
    private static int port_router = 5000;
    private static int port_server = 5040;
    private static int port_client = 5030;
    public static void main(String[] args) throws IOException {
        //create router
        Object [][] RoutingTable = new Object [10][2]; // routing table
        Boolean Running = true;
        int ind = 0; // indext in the routing table	

        //Accepting connections
        ServerSocket routerSocket = null; // server socket for accepting connections
        Socket serverSocket = null;
        Socket clientSocket = null;
        try {
          //routerSocket = new ServerSocket(port_router);
          System.out.println("routerSocket is Listening on port: " + port_router + ".");
          
          serverSocket = new Socket("10.54.64.229", port_server);
          System.out.println("serverSocket is Listening on port: " + port_server + ".");
          
          clientSocket = new Socket("10.54.64.229", port_client);
          System.out.println("clientSocket is Listening on port: " + port_client + ".");
        }
        catch (IOException e) {
          System.err.println("Could not listen on port: " + port_router + ".");
          System.exit(1);
        }
    }
}
