/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverSSL;

import br.edu.ifsc.saladebatepapo.CriptografiaRSA;
import br.edu.ifsc.saladebatepapo.serverRSA.ServidorDeChave;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Properties;
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
        
        


        while (true) {
            System.out.println("Entrou no servidor while ");
            int filesize = 6022386;
            int bytesRead;
            int current = 0;
            SSLSocket sock =  (SSLSocket) server.accept();
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

            System.out.println("saindo do receber");
            sair = true;
            enviarCriptografado(sock);
            System.out.println("passou o enviar");
            //sock.close();

        }
    }

    public void  ComparaCertificados() throws FileNotFoundException, IOException, ClassNotFoundException {

        System.out.println("Comparando certificado recebido...");

    }

    public void enviarCriptografado(SSLSocket servsock) throws IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("Entrou no enviar criptogtafado");
        // cria o nosso socket

        while (sair) {
            

            // Socket sock = new Socket("10.151.34.51", 50000);
            // Socket sock = new Socket("localhost", 50001);
            System.out.println("Conexão aceita: " + servsock);
            // File arquivo = new File(CriptografiaRSA.PATH_CHAVE_PUBLICA);
            byte[] mybytearray = null;
            System.out.println("array de bytes: " + mybytearray.toString());

            OutputStream os = servsock.getOutputStream();
            System.out.println("Enviando criptografado...");
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
            System.out.println("Pau ao iniciar serdor de chaves");
            Logger.getLogger(ServidorDeChave.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Pau ao iniciar serdor de chaves");
            Logger.getLogger(ServidorDeChave.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServidorDeChave file = new ServidorDeChave();

        file.receber();

        //file.enviarCriptografado();
    }

}
