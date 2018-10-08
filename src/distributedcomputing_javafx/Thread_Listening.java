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
public class Thread_Listening extends Thread {
    
    //constructor
    public Thread_Listening() {
        
    }
    
    
    public void run()
    {
        Socket clientSocket;
        while(Main_Router.running) {
            try {
                System.out.println("Waiting to accept connection...");
                clientSocket = Main_Router.serverSocket.accept();
                System.out.println("Someone tried to connect");
                Main_Router.AddToTable(clientSocket);
                //make new thread to handle socket
                Thread_Router st = new Thread_Router(clientSocket);
                st.start();
            }
            catch(Exception e) {
                System.out.println("ERROR ACCEPTING CONNECTION... " + e.getMessage());
                
            }
        }
    }
}
