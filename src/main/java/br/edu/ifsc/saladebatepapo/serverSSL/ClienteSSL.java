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
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ClienteSSL {

    public String chaveCriptogtafada;

    public Boolean enviaCertificadoPublico(String ipServidor, String caminho) throws IOException {
//        System.setProperty("javax.net.ssl.trustStore", "/home/user/NetBeansProjects/SalaDeBatePapo3.0/ssl/CertificadoChatTeste.cer");
        System.setProperty("javax.net.ssl.trustStore", caminho);
        System.setProperty("javax.net.ssl.trustStorePassword", "chatifsc");
        // System.out.println("Caminho cliente: " + caminho);
        //Socket sock = new Socket(ipServidor, 50002);
        Boolean autenticou = false;
        SSLSocketFactory fabrica = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sock = (SSLSocket) fabrica.createSocket(ipServidor, 50002);
        try {
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
            System.out.println("certificado enviado: ");
            autenticou = true;

        } catch (Exception e) {
            System.out.println("Erro no aperto de mão ao enviar certificado ao servidor" +e);
        }
        sock.close();

        //receberArquivo(sock);
        return autenticou;
    }

    public String receberRetornoAutenticacao(String ipServidor) throws IOException {
        System.out.println("Entrou para receber retorno autenticação");
        int tamanho = 6022386;

        int bytesRead;
        int current = 0;

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sock = (SSLSocket) factory.createSocket(ipServidor, 50003);
        //Socket sock = new Socket(ipServidor, 50002);
        //Socket sock = new Socket("10.151.34.51", 50000);

        // recebendo o arquivo
        byte[] mybytearrayRecebido = new byte[tamanho];
        InputStream is = sock.getInputStream();

        FileOutputStream fos = new FileOutputStream("Conectados.log", true);
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
        String retorno = sock.getSession().toString();

        System.out.println("recebido: " + sock.getSession());
        bos.close();
        sock.close();

        return retorno;
    }

    public void teste() {
        SSLSocketFactory sslSocketFactory
                = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            Socket socket = sslSocketFactory.createSocket("localhost", 50002);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            try (BufferedReader bufferedReader
                    = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()))) {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("Enter something:");
                    String inputLine = scanner.nextLine();
                    if (inputLine.equals("q")) {
                        break;
                    }

                    out.println(inputLine);
                    System.out.println(bufferedReader.readLine());
                }
            }

        } catch (IOException ex) {
            System.out.println("pau: " + ex);
        }
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {

        ClienteSSL file = new ClienteSSL();
        //String ip = "10.151.34.51";
        String ip = "localhost";
        file.enviaCertificadoPublico(ip, "ssl/CertificadoChat.cer");
        System.out.println("Enviou");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("demoro");
        }
        //  file.receberRetornoAutenticacao(ip);
//file.teste();
    }
}
