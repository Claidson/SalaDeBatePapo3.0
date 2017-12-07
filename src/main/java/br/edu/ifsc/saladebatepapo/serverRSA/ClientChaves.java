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
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

public class ClientChaves {

    public String chaveCriptogtafada;

    /*public void enviar() throws IOException {
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
     receberArquivo();
     }
     }*/
    public void enviaChavePublica(String ipServidor, String caminho) throws IOException {
        System.setProperty("javax.net.ssl.trustStore", caminho);
        System.setProperty("javax.net.ssl.trustStorePassword", "chatifsc");
        // cria o nosso socket
//        ServerSocket servsock = new ServerSocket(50000);
//        Socket sock = servsock.accept();
//        // Socket sock = new Socket("10.151.34.51", 50000);
//        Socket sock = new Socket(ipServidor, 50000);
//        System.out.println("Conexão aceita para enviar: " + sock);
//        File arquivo = new File(CriptografiaRSA.PATH_CHAVE_PUBLICA);
//        byte[] mybytearray = new byte[(int) arquivo.length()];
//        FileInputStream fis = new FileInputStream(arquivo);
//        BufferedInputStream bis = new BufferedInputStream(fis);
//        bis.read(mybytearray, 0, mybytearray.length);
//        OutputStream os = sock.getOutputStream();
//        System.out.println("Enviando...");
//        os.write(mybytearray, 0, mybytearray.length);
//        os.flush();
//        //receberArquivo(sock);
//
//        sock.close();
//        System.out.println("saindo do enviar a chave: ");
        //receberArquivo(sock);

        System.setProperty("javax.net.ssl.trustStore", caminho);
        System.setProperty("javax.net.ssl.trustStorePassword", "chatifsc");
        // System.out.println("Caminho cliente: " + caminho);
        //Socket sock = new Socket(ipServidor, 50002);
        Boolean autenticou = false;
        SSLSocketFactory fabrica = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sock = (SSLSocket) fabrica.createSocket(ipServidor, 50002);
        try {
            System.out.println("Conexão aceita para enviar chave: " + sock);
            File arquivo = new File(CriptografiaRSA.PATH_CHAVE_PUBLICA);
            byte[] mybytearray = new byte[(int) arquivo.length()];
            FileInputStream fis = new FileInputStream(arquivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            OutputStream os = sock.getOutputStream();
            System.out.println("Enviando...");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            //receberArquivo(sock);
            System.out.println("certificado enviado: ");
            autenticou = true;

        } catch (Exception e) {
            System.out.println("Erro no aperto de mão ao enviar certificado ao servidor " + e);
            JOptionPane.showMessageDialog(null, "Verifique sua chave \n :-( não apertou a mão.....");
            e.printStackTrace();
            System.exit(1);
        } finally {
            sock.close();
        }
    }

    public void receberArquivoCriptografado(String ipServidor) throws IOException {
        System.out.println("Entrou para receber o arquivo criptografado");
        int tamanho = 6022386;

        int bytesRead;
        int current = 0;
        //Socket sock = new Socket(ipServidor, 50001);
        //Socket sock = new Socket("10.151.34.51", 50000);

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sock = (SSLSocket) factory.createSocket(ipServidor, 50003);

        // recebendo o arquivo
        byte[] mybytearrayRecebido = new byte[tamanho];
        InputStream is = sock.getInputStream();

        FileOutputStream fos = new FileOutputStream("chaveConexaoCriptografada.key");
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
        ClientChaves file = new ClientChaves();
        //String ip = "10.151.34.51";
        String ip = "localhost";
//        file.enviaChavePublica(ip);
        System.out.println("Enviou");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("demoro");
        }
        file.receberArquivoCriptografado(ip);

    }
}
