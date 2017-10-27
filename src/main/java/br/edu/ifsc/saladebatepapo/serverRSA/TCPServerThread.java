/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverRSA;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author robson
 */
public class TCPServerThread {

    /**
     * 
     * @param port server port
     */
    private static void runServer(int port) {
        System.out.print("\nRunning TCPServerThread on port "+port+"...");
        try {
            ServerSocket listenSocket = new ServerSocket(port, 5);
            while (true) {                
                System.out.print("\n\tWaiting a connection...");
                Socket clientSocket = listenSocket.accept();
                System.out.print("\n\t\tConnected to "
                        +clientSocket.getInetAddress().toString()+" at port "
                        +clientSocket.getPort());
                Connection c = new Connection(clientSocket);
                c.start();
            }
        } catch (IOException e) {
            System.out.println("IO: "+e.getMessage());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port = 0;
        
        /* Gets and check parameter */
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
            if ((port >= 1) && (port <= 65535)) {
                runServer(port);
            } else {
                System.out.print("\nInvalid port value!!!\n\tRange: 1 - 65535");
                System.exit(1);
            }
        } else {
            System.out.print("\nParameter error!!!\n\tSet server port (1 - 65535)");
            System.exit(1);
        }
    }
    
}
