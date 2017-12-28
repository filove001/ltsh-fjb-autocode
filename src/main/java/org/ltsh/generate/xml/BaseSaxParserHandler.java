package org.ltsh.generate.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by fengjianbo on 2017/12/26.
 */
public class BaseSaxParserHandler<T> extends DefaultHandler {
    /*注意DefaultHandler是org.xml.sax.helpers包下的*/

    int bookIndex = 0;//设置全局变量，用来记录是第几本书

    String value = null;
    private T content = null;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    /**
     * 用来标识解析开始
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("SAX解析开始");

    }

    /**
     * 用来标识解析结束
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("SAX解析结束");
    }

    /**
     * 用来遍历xml文件的开始标签
     * 解析xml元素
     */
    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        //调用DefaultHandler类的startElement方法
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("book")) {
            bookIndex++;
            //创建一个book对象
//            /*Book*/ book = new Book();
            //开始解析book元素的属性
            System.out.println("======================开始遍历第"+bookIndex+"本书的内容=================");
           /* //❤已知节点的属性名时：比如已知id属性，根据属性名称获取属性值
            String value = attributes.getValue("id");
            System.out.print("book的属性值是："+value);*/
            //❤未知节点的属性名时，获取属性名和属性值
            int num=attributes.getLength();
            for(int i=0;i<num;i++){
                System.out.print("book元素的第"+(i+1)+"个属性名是："+attributes.getQName(i));
                System.out.println("---属性值是："+attributes.getValue(i));
                if (attributes.getQName(i).equals("id")) {//往book对象中塞值
//                    book.setId(attributes.getValue(i));
                }
            }
        }else if (!qName.equals("book") && !qName.equals("bookstore")) {
            System.out.print("节点名是：" + qName + "---");//此时qName获取的是节点名（标签）
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
        if (qName.equals("book")) {
//            bookList.add(book);//在清空book对象之前先保存
//            book = null;//把book清空，方便解析下一个book节点
            System.out.println("======================结束遍历第"+bookIndex+"本书的内容=================");
        }else if (qName.equals("name")) {
//            book.setName(value);
        }
        else if (qName.equals("author")) {
//            book.setAuthor(value);
        }
        else if (qName.equals("year")) {
//            book.setYear(value);
        }
        else if (qName.equals("price")) {
//            book.setPrice(value);
        }
        else if (qName.equals("language")) {
//            book.setLanguage(value);
        }
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
