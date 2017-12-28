package org.ltsh.generate.xml;

import com.alibaba.fastjson.JSONObject;
import org.ltsh.generate.bean.FieldUtils;
import org.ltsh.generate.bean.PropertyMethod;
import org.ltsh.generate.config.GlobalConfig;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjianbo on 2017/12/26.
 */
public class ConfigSaxParserHandler extends DefaultHandler {
    /*注意DefaultHandler是org.xml.sax.helpers包下的*/

    public ConfigSaxParserHandler(String rootName) {
        this.rootName = rootName;
    }
    private String rootName;
    String value = null;



    /**
     * 用来标识解析开始
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
//        System.out.println("SAX解析开始");

    }

    /**
     * 用来标识解析结束
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
//        System.out.println("SAX解析结束");
    }

    private Map<String, Object> jdbcConfig = new HashMap();
    private List<Map<String, Object>> templeteList = new ArrayList();
    /**
     * 用来遍历xml文件的开始标签
     * 解析xml元素
     */
    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        //调用DefaultHandler类的startElement方法
        super.startElement(uri, localName, qName, attributes);
        System.out.println("startElement:" + qName);
        Class beanClass = null;
        Object beanObj = null;
        if(qName.equals("jdbcConfig")) {
//            beanClass = JdbcConfig.class;
            beanObj = GlobalConfig.jdbcConfig;
        }
        int num = attributes.getLength();
        if(num > 0 && beanClass != null) {
            for (int i = 0; i < num; i++) {
                PropertyMethod propertyMethod = new PropertyMethod(attributes.getQName(i), beanClass);
                FieldUtils.setValue(propertyMethod, beanObj, attributes.getValue(i));

                System.out.print("book元素的第" + (i + 1) + "个属性名是：" + attributes.getQName(i));
                System.out.println("---属性值是：" + attributes.getValue(i));
//                if (attributes.getQName(i).equals("id")) {//往book对象中塞值
//                    book.setId(attributes.getValue(i));
//                }
            }
        }



    }

    /**
     * 用来遍历xml文件的结束标签
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //调用DefaultHandler类的endElement方法
        super.endElement(uri, localName, qName);
        //判断是否针对一本书已经遍历结束

    }

    /**
     * 获取文本
     * 重写charaters()方法时，
     * String(byte[] bytes,int offset,int length)的构造方法进行数组的传递
     * 去除解析时多余空格
     */
    @Override
    public void characters(char[] ch, int start, int length)throws SAXException {
        /**
         * ch 代表节点中的所有内容，即每次遇到一个标签调用characters方法时，数组ch实际都是整个XML文档的内容
         * 如何每次去调用characters方法时我们都可以获取不同的节点属性？这时就必须结合start（开始节点）和length（长度）
         */
        super.characters(ch, start, length);
        /*String */value = new String(ch, start, length);//value获取的是文本（开始和结束标签之间的文本）
//        System.out.println(value);//输出时会多出两个空格，是因为xml文件中空格与换行字符被看成为一个文本节点
        if(!value.trim().equals("")){//如果value去掉空格后不是空字符串
            System.out.println("节点值是：" + value);
        }
    }

    /**
     * qName获取的是节点名（标签）
     * value获取的是文本（开始和结束标签之间的文本）
     * 思考：qName和value分别在两个方法中，如何将这两个方法中的参数整合到一起？
     * 分析：要在两个方法中用同一个变量，就设置成全局变量，可以赋初值为null。
     *     可以把characters()方法中的value作成一个全局变量
     *
     * 然后在endElement()方法中对book对象进行塞值。记得要把Book对象设置为全局变量，变量共享
     */
}
