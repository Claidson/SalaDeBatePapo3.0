/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.saladebatepapo;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Claidson
 */
public class CriptografiaAES {
    static String IV ="AAAAAAAAAAA";


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



}
