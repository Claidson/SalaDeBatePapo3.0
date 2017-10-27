/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverRSA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author robson
 */
public class TCPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InetAddress srvAddr = null;
        int port = 0;
        String outMsg, inMsg;
        Scanner input = new Scanner(System.in);
        
        /* Gets parameters and check */
        if (args.length == 2) {
            try {
                srvAddr = InetAddress.getByName(args[0]);
            } catch (UnknownHostException e) {
                System.out.print("\nServer address: "+e.getMessage());
                System.exit(1);
            }
            port = Integer.parseInt(args[1]);
            if ((port < 1) && (port > 65535)) {
                System.out.print("\nInvalid port value!!!\n\tRange: 1 - 65535");
                System.exit(1);
            }
        } else {
            System.out.print("\nError!!!\n\tUses: TCPClient <ip-server> <port-server>");
            System.exit(1);
        }
        
        try {
            System.out.print("\nConnecting to "+srvAddr.toString()+" at port "+port+"... ");
            Socket sock = new Socket(srvAddr, port);
            System.out.print("[OK]");
            DataInputStream in = new DataInputStream(sock.getInputStream());
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            
            while (true) {
                System.out.print("\nMessage: ");
                outMsg = input.nextLine();
                
                /* Check message */
                if ("<close>".equals(outMsg)) {
                    /* Connection close */
                    sock.close();
                    
                    /* Close system */
                    System.exit(0);
                }
                
                /* Send message to server */
                out.writeUTF(outMsg);
                
                /* Receive message from server */
                String data = in.readUTF();
                System.out.print("\n[Response] "+data);
            
            }           
            
        } catch (IOException e) {
            System.out.print("\n\tConnection: "+e.getMessage());
        }
    }
    
}
