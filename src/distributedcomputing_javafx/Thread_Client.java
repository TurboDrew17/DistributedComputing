/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedcomputing_javafx;

import java.io.*;
import java.net.*;
/**
 *
 * @author drew
 */
public class Thread_Client extends Thread {
    private String myPort, routerAddress, destination;
    
    //constructor
    public Thread_Client(String _port, String _address, String _destination) throws IOException {
        myPort = _port;
        routerAddress = _address;
        destination = _destination;
    }
    
    
    public void run()
    {
        try {
            Socket socket = null; // socket to connect with ServerRouter
            PrintWriter out = null; // for writing to ServerRouter
            BufferedReader in = null; // for reading form ServerRouter
            InetAddress addr = InetAddress.getLocalHost();
            String host = addr.getHostAddress(); // Client machine's IP
            System.out.println("My IP is... " + host);
            //connect to the serverRouter
            try {
                System.out.println("Trying to create socket");
                socket = new Socket(routerAddress, Integer.parseInt(myPort));
                System.out.println("Connected to socket");
                out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("Created output stream");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Created input stream");
            }
            catch(UnknownHostException e) {
                System.err.println("Don't know about router: " + routerAddress);
                System.exit(1);
            }
            catch(IOException e) {
                System.err.println("Couldn't get I/O for the connection to: " + routerAddress);
                System.err.println(e.getMessage());
                System.exit(1);
            }

            ClientController.ShowWaitingOnDestination();    //connected to router, now need to connect to destination

            //wait on connection to destination
            String fromServer, toServer;

            out.println(destination);   //tell router where we want to go
            System.out.println("sent destination");
            fromServer = in.readLine(); //wait for response from router
            System.out.println("ServerRouter: " + fromServer);
            out.println(host); // Client sends the IP of its machine as initial send
            
            //give the client controller the means to send a message
            ClientController.SetOutWriter(out);

            boolean waitingOnDestination = true;
            //while communicating with router
            while((fromServer = in.readLine()) != null) {
                if(waitingOnDestination) {
                    if(!fromServer.contains("Failed"))
                    {
                        //we successfully reached destination
                        ClientController.ShowCanvas();
                        waitingOnDestination = false;
                    }
                }
                else {
                    if(fromServer.contains("Bye")) {
                        
                    }
                    else {
                        ClientController.ReceiveStroke(fromServer);
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println("RAN INTO ERROR..." + e.getMessage());
        }
    }
}
