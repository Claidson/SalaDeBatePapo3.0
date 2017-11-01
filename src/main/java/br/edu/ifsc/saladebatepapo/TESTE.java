/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo;

import static br.edu.ifsc.saladebatepapo.CriptografiaRSA.PATH_CHAVE_PRIVADA;
import br.edu.ifsc.saladebatepapo.serverRSA.ClientChaves;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author user
 */
public class TESTE {

 
    public static byte[] getBytes(File file) {
     int             len     = (int)file.length();  
      byte[]          sendBuf = new byte[len];
      FileInputStream inFile  = null;
      try {
         inFile = new FileInputStream(file);         
         inFile.read(sendBuf, 0, len);  
      } catch (FileNotFoundException fnfex) {
      } catch (IOException ioex) {
      }
 return sendBuf;
}

    public static String descriptografaRSA() throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream inputStreamChavePrivada = null;
       // ObjectInputStream inputStreamArquivoChave = null;
        System.out.println("antes de ler os arquivos");
        // Decriptografa a Mensagem usando a Chave Privada
        inputStreamChavePrivada = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
      //  inputStreamArquivoChave = new ObjectInputStream(new FileInputStream("chaveConexaoCriptografada.key"));
        System.out.println("Leu os arquivos");
        PrivateKey chavePrivada = (PrivateKey) inputStreamChavePrivada.readObject();
        //byte[] criptografado = (byte[]) inputStreamArquivoChave.readObject();
        File sourceFile = new File("chaveConexaoCriptografada.key");
        byte[] criptografado = getBytes(sourceFile);
        String textoPuro = CriptografiaRSA.decriptografa(criptografado, chavePrivada);
        System.out.println("metodo de criptografar: " + textoPuro);
        return textoPuro;

    }

    public static void main(String[] args) throws InterruptedException, IOException, FileNotFoundException, ClassNotFoundException {
        ClientChaves clientRSA = new ClientChaves();

        System.out.println("vai ir a chave");
        clientRSA.enviaChavePublica();
        System.out.println("Foi a chave");

        Thread.sleep(2000);

        System.out.println("aki antes de receber");
        clientRSA.receberArquivoCriptografado();
        Thread.sleep(2000);
        System.out.println("depois de receber");
        String chaveDescripgrafada = descriptografaRSA();
        System.out.println("chave desc: " + chaveDescripgrafada);
    }
}
