package com.alipay.sign;


import com.alipay.config.AlipayConfig;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA",new BouncyCastleProvider());
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key 支付宝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA",new BouncyCastleProvider());
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* 解密
	* @param content 密文
	* @param private_key 商户私钥
	* @param input_charset 编码格式
	* @return 解密后的字符串
	*/
	public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

	
	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}
	public static void main(String[] args) {
		try {
			String context1 = "body=测试商品订单zfb4c840e5f6d7481ca036043d26bf4c7c&buyer_email=15952009691&buyer_id=2088102185836145&discount=0.00&gmt_create=2016-08-10 18:35:32&gmt_payment=2016-08-10 18:35:33&is_total_fee_adjust=N&notify_id=a568c47a2f7901913584a00f5103f6eh2y&notify_time=2016-08-10 21:59:43&notify_type=trade_status_sync&out_trade_no=zfb4c840e5f6d7481ca036043d26bf4c7c&payment_type=1&price=1.00&quantity=1&seller_email=2469053474@qq.com&seller_id=2088421357725477&subject=测试订单zfb4c840e5f6d7481ca036043d26bf4c7c&total_fee=1.00&trade_no=2016081021001004140284072459&trade_status=TRADE_SUCCESS&use_coupon=N";

			String sign = "F9qJ3VXE2hU3PrHlqtNgQC7OkJ2qirHb3o4TQ9JqP/C/YcHz8YpAzg9T1PZbv25qWa9mTM3LM3V1W+nESSPUYf2JWQS6pKzGnbKIuVolpqdqZSvboh6GbvgBV8E5s53IbPVaTR2le44/NVkoiA8SpeTc1cfMZXBXMjaPnOInkk8=";
			String context = "body=zf1daee6238cc44813b9dfcc1d0faec555&buyer_email=15952009691&buyer_id=2088102185836145&discount=0.00&gmt_create=2016-08-10 19:56:37&gmt_payment=2016-08-10 19:56:38&is_total_fee_adjust=N&notify_id=8947ae8b47d6339717dd8671956d62fh2y&notify_time=2016-08-10 20:20:38&notify_type=trade_status_sync&out_trade_no=zf1daee6238cc44813b9dfcc1d0faec555&payment_type=1&price=1.00&quantity=1&seller_email=2469053474@qq.com&seller_id=2088421357725477&subject=zf1daee6238cc44813b9dfcc1d0faec555&total_fee=1.00&trade_no=2016081021001004140299656615&trade_status=TRADE_SUCCESS&use_coupon=N";
			boolean dwadaw = verify(context1, sign,AlipayConfig.alipay_public_key ,"utf-8");
			System.out.print(dwadaw+"神话");
			String[] test = new String[]{};
			System.out.print(test.getClass().isArray()+"哇哈哈");

			if(test instanceof String[]){
				System.out.print(dwadaw+"神话1");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
