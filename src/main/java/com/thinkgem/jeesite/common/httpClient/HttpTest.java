package com.thinkgem.jeesite.common.httpClient;

import com.thinkgem.jeesite.common.utils.MyHttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by tangaoyu on 2016/8/28.
 */
public class HttpTest {
    private static final int socketTimeout = 10000;
    private static final int connectTimeout = 30000;

    public static void main(String[] args) throws KeyStoreException, IOException {
        CloseableHttpClient httpClient = MyHttpClient.createSSLClientDefault(); /*HttpClients.createDefault();*/
        HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://kyfw.12306.cn/otn/leftTicket/init");
        StringEntity entity = new StringEntity("神话","utf-8");
        httpPost.setEntity(entity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        StatusLine statusLine = httpResponse.getStatusLine();
        if (HttpStatus.SC_OK == httpResponse.getStatusLine()
                .getStatusCode()) {
            HttpEntity entity1 = httpResponse.getEntity();
            System.out.println(entity1);
            String response = EntityUtils
                    .toString(httpResponse.getEntity());
            String response1 = response;
        }
    }
}
