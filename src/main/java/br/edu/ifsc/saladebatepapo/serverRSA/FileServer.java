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
import static br.edu.ifsc.saladebatepapo.CriptografiaRSA.PATH_CHAVE_PUBLICA;
import java.net.*;
import java.io.*;
import java.security.PublicKey;
import java.util.Properties;

public class FileServer {

    public void receberArquivo(String host) throws IOException {
        int tamanho = 6022386;

        int bytesRead;
        int current = 0;
        // Socket sock = new Socket("10.151.34.51", 50000);
        Socket sock = new Socket(host, 50000);

        // recebendo o arquivo
        byte[] mybytearrayRecebido = new byte[tamanho];
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
        System.out.println("recebido: " + bos.toString());
        bos.close();
        sock.close();
    }

    public void enviar() throws IOException {
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

    public byte[] criptografaRSA(PublicKey StringChavePublica) throws FileNotFoundException, IOException, ClassNotFoundException {
      
        
//        Properties props = new Properties();
//        InputStream in = getClass().getClassLoader().getResourceAsStream("chave.properties");
//        props.load(in);
//        in.close();
//        String senha = props.getProperty("chave");
        String senha = "RaioPerinzador17";
        System.out.println("senha"+ senha);
        ObjectInputStream inputStream = null;

        // Criptografa a Mensagem usando a Chave Pública
        // inputStream = new ObjectInputStream(new FileInputStream(ArquivoChavePublica));
        PublicKey chavePublica = (PublicKey) StringChavePublica;
        return CriptografiaRSA.criptografa(senha, chavePublica);
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        FileServer file = new FileServer();
        file.enviaChave();
        System.out.println("aki");
        ObjectInputStream inputStream = null;
        inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
        PublicKey chavePublica = (PublicKey) inputStream.readObject();
        System.out.println("2");
        byte[] teste = file.criptografaRSA(chavePublica);
        System.out.println("teste: "+ teste.toString());
    }
}
