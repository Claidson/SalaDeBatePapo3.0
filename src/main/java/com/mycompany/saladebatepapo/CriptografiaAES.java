/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
//http://manifesto.blog.br/2.0/Java/java-aes
 */
package com.mycompany.saladebatepapo;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

/**
 *
 * @author Claidson
 */
public class CriptografiaAES {
    static String IV ="1234asdf1234asdf";


    public byte[] criptografar(String mensagem, String chaveEncriptacao) throws Exception {
         byte[] textoencriptado = encrypt(mensagem, chaveEncriptacao);
        return textoencriptado;
        
    }

    public String descriptografar(byte[] textoencriptado, String chaveEncriptacao) throws Exception {
        String textodecriptado = decrypt(textoencriptado, chaveEncriptacao);
        return textodecriptado;
    }



    public static byte[] encrypt(String textopuro, String chaveencriptacao) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(textopuro.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoencriptado), "UTF-8");
    }
/*
public static String encrypt(String value, byte[] chave) {
        String retorno = null;
        try {
            SecretKeySpec spec = new SecretKeySpec(chave, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);
            Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cifra.init(Cipher.ENCRYPT_MODE, spec,paramSpec);
            byte[] cifrado = cifra.doFinal(value.getBytes());
            retorno = new BASE64Encoder().encode(cifrado);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }
    public static String decrypt(String cifra, byte[] chave) {
        String retorno = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(chave, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);
            byte[] decoded = new BASE64Decoder().decodeBuffer(cifra);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,paramSpec);
            retorno = new String(cipher.doFinal(decoded));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }
}*/

}
