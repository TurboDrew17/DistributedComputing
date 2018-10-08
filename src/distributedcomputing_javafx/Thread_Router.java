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
public class Thread_Router extends Thread {
    private Object[][] routing_table; // routing table
    private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
    private BufferedReader in; // reader (for reading from the machine connected to)
    private String inputLine, outputLine, destination, addr; // communication strings
    private Socket outSocket; // socket for communicating with a destination
    
    //constructor
    public Thread_Router(Socket client) throws IOException {
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        addr = client.getInetAddress().getHostAddress();
    }
    
    
    public void run()
    {
        try {
            // Initial sends/receives
            destination = in.readLine(); // initial read (the destination for writing)
            System.out.println("Forwarding to " + destination);
            out.println("Connected to the router."); // confirmation of connection
            
            boolean foundDestination = false;
            while(Main_Router.running && !foundDestination) {
                //loop through routing table to try to find destination
                for(int i = 0; i < 10; i++) {
                    if(destination.equals((String) Main_Router.routing_table[i][0]) || destination.equals((String) Main_Router.routing_table[i][1]))
                    {
                        outSocket = (Socket)Main_Router.routing_table[i][2]; // gets the socket for communication from the table
                        System.out.println("Found destination: " + destination);
                        outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
                        foundDestination = true;
                        break;
                    }
                }

                //if we didn't find, stop here
                if(!foundDestination) {
                    out.println("Failed to find the destination, waiting another second... " + destination);
                    return;
                }
                
                // pause thread for a second
                Thread.currentThread().sleep(1000);
            }
            
            // Communication loop
            while((inputLine=in.readLine()) != null)
            {
                System.out.println("Client/Server said: " + inputLine);
                if (inputLine.equals("Bye.")) // exit statement
                    break;
                outputLine = inputLine; // passes the input from the machine to the output string for the destination

                if ( outSocket != null){				
                        outTo.println(outputLine); // writes to the destination
                }
            }
        }
        catch (Exception e) {
            System.err.println("Could not listen to socket.");
            System.exit(1);
        }
    }
}
