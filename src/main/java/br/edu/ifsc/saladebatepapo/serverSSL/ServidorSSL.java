/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverSSL;

import br.edu.ifsc.saladebatepapo.serverRSA.ServidorDeChave;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class ServidorSSL extends Thread {

    Boolean sair = true;

    public void receber() throws IOException, FileNotFoundException, ClassNotFoundException {

        SSLServerSocket server;
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        server = (SSLServerSocket) factory.createServerSocket(50002);

        SSLServerSocket serverRetorno;
        
        serverRetorno = (SSLServerSocket) factory.createServerSocket(50003);

        while (true) {
            System.out.println("Preparando para receber certificado no servidor... ");
            int filesize = 6022386;
            int bytesRead;
            int current = 0;
            SSLSocket sock = (SSLSocket) server.accept();
            System.out.println("Conexão aceita para receber: " + sock);
            // DataOutputStream out = new DataOutputStream(sock.getOutputStream());

            // recebendo o arquivo
            byte[] mybytearrayRecebido = new byte[filesize];
            InputStream is = sock.getInputStream();

            FileOutputStream arquivoSaidaStream = new FileOutputStream("CertificadoRecebido.cer");
            BufferedOutputStream bufferSaida = new BufferedOutputStream(arquivoSaidaStream);
            bytesRead = is.read(mybytearrayRecebido, 0, mybytearrayRecebido.length);
            current = bytesRead;
            do {
                bytesRead
                        = is.read(mybytearrayRecebido, current, (mybytearrayRecebido.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);
            bufferSaida.write(mybytearrayRecebido, 0, current);
            System.out.println("recebido: " + bufferSaida.toString());
            bufferSaida.close();

            System.out.println("saindo do receber certificado ssl");
            sair = true;
            retornoAutenticacao(serverRetorno);
            System.out.println("Autenticacao enviada");
            //sock.close();

        }
    }

    public void ComparaCertificados() throws FileNotFoundException, IOException, ClassNotFoundException {

        System.out.println("Comparando certificado recebido...");

    }

    public void retornoAutenticacao(SSLServerSocket serverRetorno) throws IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("Entrou na autenticacao");
        // cria o nosso socket

        while (sair) {

            // Socket sock = new Socket("10.151.34.51", 50000);
            // Socket sock = new Socket("localhost", 50001);
      
            SSLSocket servsock = (SSLSocket) serverRetorno.accept();
            System.out.println("Conexão aceita: " + servsock);
            String retorno = "Conexão: " + servsock.toString()+"\n";
            byte[] mybytearray = retorno.getBytes();
            System.out.println("array de bytes: " + mybytearray.toString());

            OutputStream os = servsock.getOutputStream();
            System.out.println("Enviando retorno...");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            servsock.close();
            sair = false;
        }

    }

    public void run() {

        try {
            receber();
        } catch (IOException ex) {
            System.out.println("Pau ao iniciar servidor ssl");
            Logger.getLogger(ServidorDeChave.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Pau ao iniciar servidor ssl");
            Logger.getLogger(ServidorDeChave.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void teste() {

        SSLServerSocketFactory sslServerSocketFactory
                = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            ServerSocket sslServerSocket
                    = sslServerSocketFactory.createServerSocket(50002);
            System.out.println("SSL ServerSocket iniciado");
            System.out.println(sslServerSocket.toString());

            Socket socket = sslServerSocket.accept();
            System.out.println("ServerSocket accepted");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            try (BufferedReader bufferedReader
                    = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    out.println(line);
                }
            }
            System.out.println("Closed");

        } catch (IOException ex) {
            System.out.println("Pau: " + ex);

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.setProperty("javax.net.ssl.keyStore", "ssl/CertificadoChat");
        System.setProperty("javax.net.ssl.keyStorePassword", "chatifsc");
        ServidorSSL file = new ServidorSSL();

        file.receber();
//file.teste();
        //file.enviarCriptografado();
    }

}
