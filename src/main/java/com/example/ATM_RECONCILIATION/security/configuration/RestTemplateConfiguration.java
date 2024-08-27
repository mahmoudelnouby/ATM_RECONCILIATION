package com.example.ATM_RECONCILIATION.security.configuration;


import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfiguration{
	
    @Bean RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        ignoreCertificates();

//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                .loadTrustMaterial(null, acceptingTrustStrategy)
//                .build();
        SSLContext sslContext =SSLContextBuilder.create()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(csf)
                .build();

//        .setSSLSocketFactory is depricated
        CloseableHttpClient httpClient = HttpClients.custom()
                .disableRedirectHandling()
                .setConnectionManager(connectionManager)
                .build();


        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient((HttpClient) httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
    
    public void ignoreCertificates() {
    	
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                
            	public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
}