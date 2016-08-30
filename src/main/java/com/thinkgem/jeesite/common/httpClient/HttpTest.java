package com.thinkgem.jeesite.common.httpClient;

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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.*;

/**
 * Created by tangaoyu on 2016/8/28.
 */
public class HttpTest {
    private static final int socketTimeout = 10000;
    private static final int connectTimeout = 30000;

    public static void main(String[] args) throws KeyStoreException, IOException {
        CloseableHttpClient httpClient = httpsTest(); /*HttpClients.createDefault();*/
        HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.dexcoder.com/selfly/article/4048#update_dynamicDataSource");
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

    private static CloseableHttpClient httpsTest() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http",plainSF);
//指定信任密钥存储对象和连接套接字工厂

        try
        {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,"123456".toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, (String[])null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient  = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

            return httpClient;

        } catch(
                KeyStoreException e)

        {
            throw new RuntimeException(e);
        } catch(
                KeyManagementException e)

        {
            throw new RuntimeException(e);
        } catch(
                NoSuchAlgorithmException e)

        {
            throw new RuntimeException(e);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        return null;
    }
}
