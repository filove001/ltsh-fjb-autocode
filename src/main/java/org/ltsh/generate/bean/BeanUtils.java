package org.ltsh.generate.bean;

import org.ltsh.generate.bean.FieldUtils;
import org.ltsh.generate.bean.PropertyMethod;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjianbo on 2017/12/27.
 */
public class BeanUtils {
    public static <T> T mapToBean(Map source, Class<T> cl) throws IllegalAccessException, InstantiationException {
        List<Field> fieldList = FieldUtils.getFieldList(cl);
        T beanObj = null;
        for (Object key: source.keySet()) {
            for (Field field: fieldList) {
                if(key.equals(field.getName())) {
                    if(beanObj == null) {
                        beanObj = cl.newInstance();
                    }
                    PropertyMethod propertyMethod = new PropertyMethod(field.getName(), cl);
                    FieldUtils.setValue(propertyMethod, beanObj, source.get(key));
                }
            }
        }
        return beanObj;
    }
}
