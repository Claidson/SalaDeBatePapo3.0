package br.edu.ifsc.saladebatepapo.serverRSA;

import br.edu.ifsc.saladebatepapo.CriptografiaRSA;
import java.net.*;
import java.io.*;
import java.security.PublicKey;

public class ServidorDeChave {

    public void receber() throws IOException, FileNotFoundException, ClassNotFoundException {
        int filesize = 6022386;

        int bytesRead;
        int current = 0;
        ServerSocket servsock = new ServerSocket(50000);
//        while (true) {
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
        enviarCriptografado();
        System.out.println("passou o enviar");
        sock.close();
//        }
    }

    public byte[] criptografaRSA() throws FileNotFoundException, IOException, ClassNotFoundException {

//        Properties props = new Properties();
//        InputStream in = getClass().getClassLoader().getResourceAsStream("chave.properties");
//        props.load(in);
//        in.close();
//        String senha = props.getProperty("chave");
        String senha = "RaioPerinzador17";
        System.out.println("senha " + senha);
        ObjectInputStream inputStream = null;

        // Criptografa a Mensagem usando a Chave Pública
        inputStream = new ObjectInputStream(new FileInputStream("chavePublicaRecebida.key"));
        final PublicKey chavePublica = (PublicKey) inputStream.readObject();
        return CriptografiaRSA.criptografa(senha, chavePublica);
    }

    public void enviaChave() throws IOException {

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

    }

    public void enviarCriptografado() throws IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("Entrou no enviar");
        // cria o nosso socket
       ServerSocket servsock = new ServerSocket(50001);
       Socket sock = servsock.accept();
        // Socket sock = new Socket("10.151.34.51", 50000);
        // Socket sock = new Socket("localhost", 50001);
        System.out.println("Conexão aceita: " + sock);
       // File arquivo = new File(CriptografiaRSA.PATH_CHAVE_PUBLICA);
        byte[] mybytearray = criptografaRSA();
       
        OutputStream os = sock.getOutputStream();
        System.out.println("Enviando...");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();

        sock.close();

       
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServidorDeChave file = new ServidorDeChave();
        file.receber();
        System.out.println("saiu");
        //file.enviarCriptografado();
    }

}
