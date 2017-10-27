/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverRSA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author robson
 */
public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    
    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            
        } catch (IOException e) {
            System.out.println("Connection: "+e.getMessage());
        }
    }
    
    public void run() {
        try {
            while (true) {
                /* Receive message from client */
                String data = in.readUTF();
                System.out.print("\n\t[Receive - "+clientSocket.getInetAddress().toString()
                        +":"+clientSocket.getPort()+"]: "+data);
                
                /* Send the response to client */
                data = "Received by server";
                out.writeUTF(data);
            
            }
            
            
        } catch (IOException e) {
            System.out.println("IO: "+e.getMessage());
        }
    }
}
