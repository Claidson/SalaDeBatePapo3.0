package br.edu.ifsc.saladebatepapo.serverRSA;

import java.net.*;
import java.io.*;

public class FileClient {

    public void receber() throws IOException {
        int filesize = 6022386;

        int bytesRead;
        int current = 0;
        ServerSocket servsock = new ServerSocket(50000);
//        while (true) {
            Socket sock = servsock.accept();
            System.out.println("Conexão aceita: " + sock);
       // DataOutputStream out = new DataOutputStream(sock.getOutputStream());

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
//        }
    }

    public static void main(String[] args) throws IOException {
        FileClient file = new FileClient();
        file.receber();
    }

}
