/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class ConectarThread extends Thread {

    int porta;
    String nome;
    BatePapo sala;
    String grupo;
    String chave;
    byte[] buffer;
    DatagramPacket pacote;
    CriptografiaAES criptografia = new CriptografiaAES();
    Boolean stop = true;
    MulticastSocket socket;
    DatagramPacket msgOut;
    

    public ConectarThread(int porta, String nome, BatePapo sala, String grupo, String chave) {
        this.porta = porta;
        this.nome = nome;
        this.sala = sala;
        this.grupo = grupo;
        this.chave = chave;
        
    }

    public void enviarMensagem(byte[] data) {
        
        try {
            msgOut = new DatagramPacket(data, data.length, InetAddress.getByName(grupo), porta);
            socket.send(msgOut);
        } catch (IOException ex) {
            System.out.println("ConectarThread - deu pal para enviar mensagem");
        }
    }
    public void parar(){
        stop = false;
    }

    public void run() {
        try {
            InetAddress group = InetAddress.getByName(grupo);
            socket = new MulticastSocket(porta);
            socket.joinGroup(group);
            buffer = new byte[1000];
            pacote = new DatagramPacket(buffer, buffer.length);
  
            while (stop) {
                buffer = new byte[1024];
                pacote = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacote);
               

                byte mensagemBytes[] = Arrays.copyOf(pacote.getData(), pacote.getLength());
                String mensagem = criptografia.descriptografar(mensagemBytes, chave);

                sala.inserirTexto(new String(mensagem));
                pacote = new DatagramPacket(buffer, buffer.length);

            }

        } catch (Exception e) {
            System.out.println("Deu pal no ConectarThread RUN():" + e);
        }
    }
}
