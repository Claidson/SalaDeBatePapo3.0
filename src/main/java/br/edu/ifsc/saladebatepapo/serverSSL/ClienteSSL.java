/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverSSL;

/**
 *
 * @author aluno
 */
import br.edu.ifsc.saladebatepapo.CriptografiaRSA;
import java.net.*;
import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ClienteSSL {

    public String chaveCriptogtafada;

    public void enviaChavePublica(String ipServidor) throws IOException {

        Socket sock = new Socket(ipServidor, 50000);
        System.out.println("Conexão aceita para enviar: " + sock);
        File arquivo = new File("ssl/certificado1.crt");
        byte[] mybytearray = new byte[(int) arquivo.length()];
        FileInputStream fis = new FileInputStream(arquivo);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = sock.getOutputStream();
        System.out.println("Enviando...");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        //receberArquivo(sock);

        sock.close();
        System.out.println("saindo do enviar a chave: ");
        //receberArquivo(sock);
    }

    public void receberArquivoCriptografado(String ipServidor) throws IOException {
        System.out.println("Entrou para receber o arquivo criptografado");
        int tamanho = 6022386;

        int bytesRead;
        int current = 0;

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sock = (SSLSocket) factory.createSocket(ipServidor, 50002);
        //Socket sock = new Socket(ipServidor, 50002);
        //Socket sock = new Socket("10.151.34.51", 50000);

        // recebendo o arquivo
        byte[] mybytearrayRecebido = new byte[tamanho];
        InputStream is = sock.getInputStream();

        FileOutputStream fos = new FileOutputStream("tste.cer");
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

        System.out.println("recebido: " + bos.toString());
        bos.close();
        sock.close();
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        ClienteSSL file = new ClienteSSL();
        //String ip = "10.151.34.51";
        String ip = "localhost";
        file.enviaChavePublica(ip);
        System.out.println("Enviou");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("demoro");
        }
        file.receberArquivoCriptografado(ip);

    }
}
