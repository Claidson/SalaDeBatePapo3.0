package br.edu.ifsc.saladebatepapo.serverSSL;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;

public class AssinaturaDigital {

    public static void main(String args[]) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //Remetente Gera Assinatura Digital para uma Mensagem
        RemetenteAssiDig remetenteAssiDig = new RemetenteAssiDig();
        String mensagem = "Exemplo de mensagem.";
        byte[] assinatura = remetenteAssiDig.geraAssinatura(mensagem);
        //Guarda Chave Pública para ser Enviada ao Destinatário
        PublicKey pubKey = remetenteAssiDig.getPubKey();

        //Destinatário recebe dados correto
        DestinatarioAssiDig destinatarioAssiDig = new DestinatarioAssiDig();
        destinatarioAssiDig.recebeMensagem(pubKey, mensagem, assinatura);

        //Destinatário recebe mensagem alterada
        String msgAlterada = "Exemplo de mensagem alterada.";
        destinatarioAssiDig.recebeMensagem(pubKey, msgAlterada, assinatura);

        //Criando outra Assinatura
        String mensagem2 = "Exemplo de mensagem.";
        byte[] assinatura2 = remetenteAssiDig.geraAssinatura(mensagem2);
        //Guarda Chave Pública para ser Enviada ao Destinatário
        PublicKey pubKey2 = remetenteAssiDig.getPubKey();

        //Destinatário recebe outra assinatura
        destinatarioAssiDig.recebeMensagem(pubKey, mensagem, assinatura2);

        //Destinatário recebe outra chave pública
        destinatarioAssiDig.recebeMensagem(pubKey2, mensagem, assinatura);

    }

}
