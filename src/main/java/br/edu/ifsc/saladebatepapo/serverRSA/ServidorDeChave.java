package br.edu.ifsc.saladebatepapo.serverRSA;

import br.edu.ifsc.saladebatepapo.CriptografiaRSA;
import java.net.*;
import java.io.*;
import java.security.PublicKey;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorDeChave extends Thread {

    Boolean sair = true;

    public void receber() throws IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("Entrou no servidor chave ");
        ServerSocket servsock = new ServerSocket(50000);
        ServerSocket servsockCriptografado = new ServerSocket(50001);
        while (true) {
            System.out.println("Entrou no servidor while ");
            int filesize = 6022386;
            int bytesRead;
            int current = 0;
            Socket sock = servsock.accept();
            System.out.println("Conexão aceita para receber: " + sock);
            // DataOutputStream out = new DataOutputStream(sock.getOutputStream());

            // recebendo o arquivo
            byte[] mybytearrayRecebido = new byte[filesize];
            InputStream is = sock.getInputStream();

            FileOutputStream arquivoSaidaStream = new FileOutputStream("chavePublicaRecebida.key");
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
            enviarCriptografado(servsockCriptografado);
            System.out.println("passou o enviar");
            sock.close();

        }
    }

    public byte[] criptografaRSA() throws FileNotFoundException, IOException, ClassNotFoundException {

        Properties props = new Properties();
        FileInputStream arquivo = new FileInputStream(
                "chave.properties");
        props.load(arquivo);
        arquivo.close();
        String senha = props.getProperty("chave");
        System.out.println("senha " + senha);
        ObjectInputStream inputStream = null;

        // Criptografa a Mensagem usando a Chave Pública
        inputStream = new ObjectInputStream(new FileInputStream("chavePublicaRecebida.key"));
        final PublicKey chavePublica = (PublicKey) inputStream.readObject();
        return CriptografiaRSA.criptografa(senha, chavePublica);
    }

    public void enviarCriptografado(ServerSocket servsock) throws IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("Entrou no enviar criptogtafado");
        // cria o nosso socket

        while (sair) {
            System.out.println("Entrou no enviar criptogtafado while");
            Socket sock = servsock.accept();
            // Socket sock = new Socket("10.151.34.51", 50000);
            // Socket sock = new Socket("localhost", 50001);
            System.out.println("Conexão aceita: " + sock);
            // File arquivo = new File(CriptografiaRSA.PATH_CHAVE_PUBLICA);
            byte[] mybytearray = criptografaRSA();
            System.out.println("array de bytes: " + mybytearray.toString());

            OutputStream os = sock.getOutputStream();
            System.out.println("Enviando criptografado...");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            sock.close();
            sair = false;
        }

    }

    /* public void enviaChave() throws IOException {

     // cria o nosso socket
     //        ServerSocket servsock = new ServerSocket(50000);
     //        Socket sock = servsock.accept();
     // Socket sock = new Socket("10.151.34.51", 50000);
     Socket sock = new Socket("localhost", 50000);
     System.out.println("Conexão aceita: " + sock);
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

     }*/
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
