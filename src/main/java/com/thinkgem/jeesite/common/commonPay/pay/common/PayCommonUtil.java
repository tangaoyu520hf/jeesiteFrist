package com.thinkgem.jeesite.common.commonPay.pay.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class PayCommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(PayCommonUtil.class);

    /**
     * 将map转成对象
     * @param type
     * @param map
     * @return
     */
    public static Object convertMap(Class type, Map map){
        Object obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
            obj = type.newInstance(); // 创建 JavaBean 对象
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("---map to object error 转换失败");
        }
        return obj;
    }
}
