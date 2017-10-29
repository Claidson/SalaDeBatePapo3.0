/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverRSA;

/**
 *
 * @author aluno
 */
import br.edu.ifsc.saladebatepapo.CriptografiaRSA;
import java.net.*;
import java.io.*;

public class FileServer {
    
    public void receber() throws IOException{
        int filesize = 6022386;

        int bytesRead;
        int current = 0;
       // Socket sock = new Socket("10.151.34.51", 50000);
        Socket sock = new Socket("localhost", 50000);
       // DataOutputStream out = new DataOutputStream(sock.getOutputStream());

        
        
    /*
    
      System.out.println("Conexão aceita: " + sock);
      // envia o arquivo (transforma em byte array)
      File arquivo = new File (PATH_CHAVE_PUBLICA);
       System.out.println("Caminho: " + arquivo.getPath()+ "Conteudo: "+ arquivo.toString());
      byte [] mybytearray  = new byte [(int)arquivo.length()];
      FileInputStream fis = new FileInputStream(arquivo);
      BufferedInputStream bis = new BufferedInputStream(fis);
      bis.read(mybytearray,0,mybytearray.length);
      OutputStream os = sock.getOutputStream();
      System.out.println("Enviando...");
      os.write(mybytearray,0,mybytearray.length);
      os.flush();
     // sock.close();
      
*/

        // recebendo o arquivo
        byte[] mybytearrayRecebido = new byte[filesize];
        InputStream is = sock.getInputStream();

        FileOutputStream fos = new FileOutputStream("chaveConexaoRecebida.key");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearrayRecebido, 0, mybytearrayRecebido.length);
        current = bytesRead;
        do {
            bytesRead
                    = is.read(mybytearrayRecebido, current, (mybytearrayRecebido.length - current));
            if (bytesRead >= 0) {
                current += bytesRead;
            }
        } while (bytesRead > -1);
        bos.write(mybytearrayRecebido, 0, current);
        System.out.println("recebido: "+bos.toString());
        bos.close();
        sock.close();
    }
    
    public void enviar() throws IOException{
         // cria o nosso socket
        ServerSocket servsock = new ServerSocket(50000);
        while (true) {
            Socket sock = servsock.accept();
            System.out.println("Conexão aceita: " + sock);
            // envia o arquivo (transforma em byte array)
            File arquivo = new File(CriptografiaRSA.PATH_CHAVE_PUBLICA);
            byte[] mybytearray = new byte[(int) arquivo.length()];
            FileInputStream fis = new FileInputStream(arquivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            OutputStream os = sock.getOutputStream();
            System.out.println("Enviando...");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            sock.close();
        }
    }

    public static void main(String[] args) throws IOException {
     FileServer file = new FileServer();
     file.enviar();
        
    }
}

