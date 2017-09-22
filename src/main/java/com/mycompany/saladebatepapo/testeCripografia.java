/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.saladebatepapo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class testeCripografia {

    public static void main(String[] args) {
        CriptografiaAES criptografia = new CriptografiaAES();
        String teste;
        String chave;

        byte[] criptografado = null;
        String descriptografado;

        chave = "raioperinzador12";
        teste = "teste";

        try {
            criptografado = criptografia.criptografar(teste, chave);
            System.out.print("Texto criptografado: ");

            for (int i = 0; i < criptografado.length; i++) {
                System.out.print(new Integer(criptografado[i]) + " ");
            }
            
             String decriptado = criptografia.descriptografar(criptografado, chave);
             System.out.println("\ndescriptografado: "+ decriptado);
             
        } catch (Exception ex) {
            System.out.println("pau no criptografar");
            Logger.getLogger(testeCripografia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       

    }
}
