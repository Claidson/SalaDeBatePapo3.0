/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.saladebatepapo.serverSSL;

import java.net.URL;
 
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
 
public class HTTPSSimpleClient {
    // Disable the hostname verification for demo purpose
    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
    }
     
    public static void main(String[] args){
        // Initialize configuration
        System.setProperty("javax.net.ssl.trustStore", "ssl/claidson.crt");
        System.setProperty("javax.net.ssl.trustStoreType", "jks");
         
        try{
            URL url = new URL("https://127.0.0.1:9999");
            HttpsURLConnection client = (HttpsURLConnection) url.openConnection();
             
            System.out.println("RETURN : "+client.getResponseCode());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}