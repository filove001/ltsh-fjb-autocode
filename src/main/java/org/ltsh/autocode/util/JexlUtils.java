package org.ltsh.autocode.util;

import org.apache.commons.jexl3.*;

import java.util.Map;

/**
 * Created by fengjb-it on 2016/2/14 0014.
 */
public class JexlUtils {
    public static Object executeStringReturn(JexlEngine jexl, String jexlExp, Map<String, Object> jexlMap) {
        // Create or retrieve an engine
        if(jexl == null) {
            jexl = new JexlBuilder().create();
        }
        JexlScript script = jexl.createScript(jexlExp);

        // Create an expression
//        JexlExpression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        if(jexlMap != null) {
            for (String key : jexlMap.keySet()) {
                jc.set(key, jexlMap.get(key));
            }
        }

        // Now evaluate the expression, getting the result

        Object o = script.execute(jc);
        return o;
    }
    public static Object executeStringReturn(String jexlExp, Map<String, Object> jexlMap) {
        return executeStringReturn(null, jexlExp, jexlMap);
    }
}
