package com.thinkgem.jeesite.common.commonPay.pay.wxpay;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by zl on 15-3-18.
 */
public class Signature {

    public static void main(String[] a){
        Map<String,Object> map = new HashMap<String,Object>();
        JsapiDto js = new JsapiDto();
        map.put("appId","wx688d4bb48d13d1f3");
        js.setAppId("wx688d4bb48d13d1f3");
        map.put("timeStamp","1427080080");
        js.setTimeStamp("1427080080");
        map.put("nonceStr","n6odkru55fltldd7g7bvpotf98hm0s");
        js.setNonceStr("n6odkru55fltldd7g7bvpotf98hm0s");
        String pk = "wx20150323110800600921b5650858301943";
        map.put("package","prepay_id=wx20150323110800600921b5650858301943");
        String pp = String.format("prepay_id={0}", pk);
        System.out.println("pp:"+pp);
        js.setWx_package(pp);
        map.put("signType","MD5");
        js.setSignType("MD5");

        try {
            String sn = getSign(js);
            System.out.println(sn);
        }catch (Exception e){

        }


//        JsapiDto ji = new JsapiDto();
//        ji.setAppId("wx688d4bb48d13d1f3");
//        ji.setNonceStr("22xilzr0p07ef3uw3x0o6wfbe1ujet");
//        ji.setSignType("MD5");
//        ji.setTimeStamp("1426942111");
//        try {
//            String sn = getSign(ji);
//            System.out.println(sn);
//        }catch (Exception e){
//
//        }
    }

    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o) throws IllegalAccessException {
        String result = getStrSign(o);
        result += "key=" + Configure.getKey();
        System.out.println("-----------------Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        System.out.println("-----------------Sign Result:" + result);
        return result;
    }

    private static String getStrSign(Object o) throws IllegalAccessException{
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                String v = f.get(o).toString();
                //微信package参数特殊处理（与java关键词相同）
                String n = f.getName().equals("wx_package")?"package":f.getName();
                list.add(n + "=" + v + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        return sb.toString();
    }

    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @param key 商户秘钥
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o,String key) throws IllegalAccessException {
        String result = getStrSign(o);
        result += "key=" + key;
        System.out.println("-----------------Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        System.out.println("-----------------Sign Result:" + result);
        return result;
    }

    public static String getSign(Map<String,Object> map){
        String result = getStrSign(map);
        result += "key=" + Configure.getKey();
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }

    private static String getStrSign(Map<String,Object> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                String v = entry.getValue().toString();

                //微信package参数特殊处理（与java关键词相同）
                String k =  entry.getKey().equals("wx_package")?"package": entry.getKey();
                list.add(k + "=" + v + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        return sb.toString();
    }

    public static String getSign(Map<String,Object> map,String key){
        String result = getStrSign(map);
        result += "key=" + key;
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }

    /**
     * 从API返回的XML数据里面重新计算一次签名
     * @param responseString API返回的XML数据
     * @return 新鲜出炉的签名
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static String getSignFromResponseString(String responseString) throws IOException, SAXException, ParserConfigurationException {
        Map<String,Object> map = XMLParser.getMapFromXML(responseString);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return Signature.getSign(map);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString) throws ParserConfigurationException, IOException, SAXException {

        Map<String,Object> map = XMLParser.getMapFromXML(responseString);
        Util.log(map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
            Util.log("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        Util.log("服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = Signature.getSign(map);

        if(!signForAPIResponse.equals(signFromAPIResponse)){
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            Util.log("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        Util.log("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     * @param responseString API返回的XML数据字符串
     * @param key  签名验证的key
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString,String key) throws ParserConfigurationException, IOException, SAXException {

        Map<String,Object> map = XMLParser.getMapFromXML(responseString);
        Util.log(map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
            Util.log("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        Util.log("服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = Signature.getSign(map,key);

        if(!signForAPIResponse.equals(signFromAPIResponse)){
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            Util.log("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        Util.log("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机字符
     * @return
     */
    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 时间戳
     * @return
     */
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
