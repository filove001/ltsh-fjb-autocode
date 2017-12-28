package org.ltsh.generate.bean;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengjianbo on 2017/11/7.
 */
public class FieldUtils {
    public static List<Field> getFieldList(Class classT) {
        Class tempClass = classT;
        List<Field> fieldList = new ArrayList<Field>();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            Field[] declaredFields = tempClass.getDeclaredFields();
            for (Field field :
                    declaredFields) {
                if (field.getName().equals("serialVersionUID") || field.getName().equals("$change") || field.getName().indexOf("shadow$") != -1) {
                    continue;
                }
                fieldList.add(field);
            }
//            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }
    public static void setValue(PropertyMethod propertyMethod, Object obj, Object value) {
        Class toType = propertyMethod.getReturnType();
        Method method = propertyMethod.getWriteMethod();
        setValue(obj, method, value, toType);
    }
    public static void setValue(Object obj, Method method, Object[] value, Class toType) {

        try {
            method.invoke(obj, value);
//            if(toType == Integer.class || toType == Integer.TYPE) {
//                method.invoke(obj, (Integer)value);
//            } else if(toType == Double.class || toType == Double.TYPE) {
//                method.invoke(obj, ((Number)value).doubleValue());
//            } else if(toType == Boolean.class || toType == Boolean.TYPE) {
//                method.invoke(obj, ((Number)value).intValue() == 0 ? false : true);
//            } else if(toType == Byte.class || toType == Byte.TYPE) {
//                method.invoke(obj, ((Number)value).byteValue());
//            } else if(toType == Character.class || toType == Character.TYPE) {
//                method.invoke(obj, ((String)value));
//            } else if(toType == Short.class || toType == Short.TYPE) {
//                method.invoke(obj, ((Number)value).shortValue());
//            } else if(toType == Long.class || toType == Long.TYPE) {
//                method.invoke(obj, ((Number)value).longValue());
//            } else if(toType == Float.class || toType == Float.TYPE) {
//                method.invoke(obj, ((Number)value).floatValue());
//            } else if(toType == BigInteger.class) {
//                throw new RuntimeException("Don't know about " + toType);
//            } else if(toType == BigDecimal.class) {
//                method.invoke(obj, new BigDecimal(value + ""));
//            } else if(toType == String.class) {
//                method.invoke(obj, String.valueOf(value));
//            } else {
//                throw new RuntimeException("Don't know about " + toType);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setValue(Object obj, Method method, Object value, Class toType) {
        try {
            if(toType == Integer.class || toType == Integer.TYPE) {
                method.invoke(obj, (Integer)value);
            } else if(toType == Double.class || toType == Double.TYPE) {
                method.invoke(obj, ((Number)value).doubleValue());
            } else if(toType == Boolean.class || toType == Boolean.TYPE) {
                method.invoke(obj, ((Number)value).intValue() == 0 ? false : true);
            } else if(toType == Byte.class || toType == Byte.TYPE) {
                method.invoke(obj, ((Number)value).byteValue());
            } else if(toType == Character.class || toType == Character.TYPE) {
                method.invoke(obj, ((String)value));
            } else if(toType == Short.class || toType == Short.TYPE) {
                method.invoke(obj, ((Number)value).shortValue());
            } else if(toType == Long.class || toType == Long.TYPE) {
                method.invoke(obj, ((Number)value).longValue());
            } else if(toType == Float.class || toType == Float.TYPE) {
                method.invoke(obj, ((Number)value).floatValue());
            } else if(toType == BigInteger.class) {
                throw new RuntimeException("Don't know about " + toType);
            } else if(toType == BigDecimal.class) {
                method.invoke(obj, new BigDecimal(value + ""));
            } else if(toType == String.class) {
                method.invoke(obj, String.valueOf(value));
            } else {
                throw new RuntimeException("Don't know about " + toType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
