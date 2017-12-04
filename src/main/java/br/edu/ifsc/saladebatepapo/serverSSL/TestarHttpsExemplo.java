package br.edu.ifsc.saladebatepapo.serverSSL;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

/*

openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout chaveA.key -out haveA.crt
 */
public class TestarHttpsExemplo {

    public static void main(String[] args) {
        new TestarHttpsExemplo().testIt("http://10.151.34.51");
    }

    public void carregarCertificado() {
        String PATH_CHAVE = "ssl/claidson.crt";
        File arquivo = new File(PATH_CHAVE);
        byte[] mybytearray = new byte[(int) arquivo.length()];
        FileInputStream fis;
        try {
            fis = new FileInputStream(arquivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);

        } catch (FileNotFoundException ex) {
            System.out.println("Chave crt não localizada");
            Logger.getLogger(TestarHttpsExemplo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
             System.out.println("Chave crt não deu pau");
            Logger.getLogger(TestarHttpsExemplo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void testIt(String https_url) {
        try {
            URL url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            

            if (con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String line;
                String response = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                System.out.println("reponse: "+response);
            }
            
            con.getCipherSuite();
            Certificate[] certs = con.getServerCertificates();

            print_https_cert(con);
            print_content(con);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void print_https_cert(HttpsURLConnection con) {
        if (con != null) {
            try {
                System.out.println("Codigo de resposta da URL : " + con.getResponseCode());
                System.out.println("Suide de Criptografia : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    System.out.println("Tipo do Certificado........: " + cert.getType());
                    System.out.println("Codigo Hash do Certificado.: " + cert.hashCode());
                    System.out.println("Algoritmo da Chave Publica.: " + cert.getPublicKey().getAlgorithm());
                    System.out.println("Formato do Chave Publica...: " + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }
            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void print_content(HttpsURLConnection con) {
        if (con != null) {
            try {
                System.out.println("****** Conteudo da URL ********");
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input;
                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
