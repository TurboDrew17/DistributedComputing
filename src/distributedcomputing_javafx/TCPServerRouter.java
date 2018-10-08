package distributedcomputing_javafx;

import java.net.*;
import java.io.*;

public class TCPServerRouter {
  public static void main(String[] args) throws IOException {
    Socket clientSocket = null; // socket for the thread
    Object [][] RoutingTable = new Object [10][2]; // routing table
	  int SockNum = 5010; // port number
		Boolean Running = true;
		int ind = 0; // indext in the routing table	

	  //Accepting connections
    ServerSocket serverSocket = null; // server socket for accepting connections
    try {
      serverSocket = new ServerSocket(SockNum);
      System.out.println("ServerRouter is Listening on port: " + SockNum + ".");
    }
    catch (IOException e) {
      System.err.println("Could not listen on port: " + SockNum + ".");
      System.exit(1);
    }
    
    try {
        //192.168.56.1:3000
        RoutingTable[0][0] = "10.54.64.229";
        RoutingTable[0][1] = new Socket("10.54.64.229", 5010);  //server
        RoutingTable[1][0] = "192.168.56.1";
        RoutingTable[1][1] = new Socket("192.168.56.1", 5010);  //server
    }
    catch (Exception e){
        System.out.println("Failed to get the routing table");
    }
	
    // Creating threads with accepted connections
    while (Running == true)
    {
      try {
        clientSocket = serverSocket.accept();
        SThread t = new SThread(RoutingTable, clientSocket, ind); // creates a thread with a random port
        t.start(); // starts the thread
        ind++; // increments the index
        System.out.println("ServerRouter connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
      }
      catch (IOException e) {
        System.err.println("Client/Server failed to connect.");
        System.exit(1);
      }
    }//end while

    //closing connections
    clientSocket.close();
    serverSocket.close();
    System.out.println("ServerRouter closed sockets... ");
  }
}