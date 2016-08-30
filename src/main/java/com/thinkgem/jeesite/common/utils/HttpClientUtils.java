package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.commonPay.pay.unionpay.sdk.BaseHttpSSLSocketFactory;
import com.thinkgem.jeesite.common.commonPay.pay.unionpay.sdk.LogUtil;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 使用apache
 * Created by tangaoyu on 2016/8/30.
 */
public abstract class HttpClientUtils {
    /**
     *  信任所有 访问https 使用
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault(){
        try {
            //建立一个信任任何密钥的策略。代码很简单，不去考虑证书链和授权类型，均认为是受信任的：
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
/*           HttpClient既能处理常规http协议，又能支持https，
            根源在于在连接管理器中注册了不同的连接创建工厂。
            当访问url的schema为http时，调用明文连接套节工厂来建立连接；
            当访问url的schema为https时，调用SSL连接套接字工厂来建立连接。对于http的连接我们不做修改，只针对使用SSL的https连接来进行自定义：*/
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return  HttpClients.createDefault();
    }

    /**
     * 根据证书加载
     * @param path
     * @param certPassword
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient createSSLClientByCert(String path,String certPassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(path));

        try {
            keyStore.load(instream, certPassword.toCharArray());
        } catch (CertificateException var8) {
            var8.printStackTrace();
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, (String[])null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
       /* SSLConnectionSocketFactory sslsf1 = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, (String[])null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);*/
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
    /**
     *  原生解决 访问https 访问
     * @param encoding  编码集
     * @param url       url
     * @param method    请求方法
     * @return
     * @throws ProtocolException
     */
    private HttpURLConnection createConnectionGet(String encoding,URL url,String method) throws ProtocolException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            LogUtil.writeErrorLog(e.getMessage(), e);
            return null;
        }
        httpURLConnection.setConnectTimeout(30000);// 连接超时时间
        httpURLConnection.setReadTimeout(30000);// 读取结果超时时间
        httpURLConnection.setUseCaches(false);// 取消缓存
        httpURLConnection.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded;charset=" + encoding);
        httpURLConnection.setRequestMethod(method);
        if ("https".equalsIgnoreCase(url.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
            husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
            husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
            return husn;
        }
        return httpURLConnection;
    }
}
