package br.edu.ifsc.saladebatepapo.serverRSA;

import java.net.*;
import java.io.*;

public class FileClient {

    public void receber() throws IOException {
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

        FileOutputStream arquivoSaidaStream = new FileOutputStream("chaveConexaoRecebida.key");
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
        sock.close();
    }

    public static void main(String[] args) throws IOException {
        FileClient file = new FileClient();
        file.receber();
    }

}
