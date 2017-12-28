package org.ltsh.generate.xml;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.dom.DeferredCDATASectionImpl;
import com.sun.org.apache.xerces.internal.dom.DeferredTextImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjianbo on 2017/12/26.
 */
public class ReadXml {
    public static void main(String[] args) {
        String xmlPath = "D:\\work\\ideaWork\\code-generate\\src\\main\\resources\\templete\\autoConfig.xml";
        Map configuration = xmlToMap(new File(xmlPath), "configuration");
        System.out.println(JSONObject.toJSONString(configuration));
    }
    /**
     * xml转map
     * @param file
     * @param rootName
     * @return
     */
    public static Map xmlToMap(File file, String rootName){
        Document document = null;
        // 建立DocumentBuilderFactory对象
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 建立DocumentBuilder对象
            builder = builderFactory.newDocumentBuilder();
            // 用DocumentBuilder对象的parse方法引入文件建立Document对象

            document = builder.parse(new FileInputStream(file));
            NodeList xml = document.getElementsByTagName(rootName);
            // 测试1：找出所有province标签，返回NodeList

            searchAndShow(xml.item(0), map);
            // 测试2：遍历所有节点
            //searchAndShow(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * xml转map
     * @param inputStream
     * @param rootName
     * @return
     */
    public static Map xmlToMap(InputStream inputStream, String rootName){
        Document document = null;
        // 建立DocumentBuilderFactory对象
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 建立DocumentBuilder对象
            builder = builderFactory.newDocumentBuilder();
            // 用DocumentBuilder对象的parse方法引入文件建立Document对象

            document = builder.parse(inputStream);
            NodeList xml = document.getElementsByTagName(rootName);
            // 测试1：找出所有province标签，返回NodeList

            searchAndShow(xml.item(0), map);
            // 测试2：遍历所有节点
            //searchAndShow(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 递归查找
     */
    public static void searchAndShow(Node nodeRoot, Map map) {
        NodeList nodeList = nodeRoot.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Map tempMap = null;
            Node node = nodeList.item(i);
            if("#text".equals(node.getNodeName())) {
                continue;
            }
            NodeList childNodes = node.getChildNodes();
            boolean isSon = false;
            if(childNodes != null && childNodes.getLength() > 0) {
                if(node.getChildNodes().getLength() == 1 && "".equals(node.getChildNodes().item(0).getTextContent()) &&
                        (node.getChildNodes().item(0).getAttributes() == null || node.getChildNodes().item(0).getAttributes().getLength() == 0)) {

                } else {
                    isSon = true;
                }
            }
            boolean textChild = isTextChild(node);


            if(textChild) {
                map.put(node.getNodeName(), node.getTextContent());
                continue;
            } else {
                tempMap = setAttributes(node, map);
            }
            if(isSon) {
                // 若包含子节点，则递归遍历
                if(tempMap != null) {
                    recursion(node, tempMap);
                } else {
                    recursion(node, map);
                }



            }
        }
    }

    /**
     * 递归查找
     */
    public static void searchAndShow(Node nodeRoot, List list) {
        NodeList nodeList = nodeRoot.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Map tempMap = new HashMap();
            Node node = nodeList.item(i);
            if("#text".equals(node.getNodeName())) {
                continue;
            }
            NodeList childNodes = node.getChildNodes();
            boolean isSon = false;
            if(childNodes != null && childNodes.getLength() > 0) {
                if(node.getChildNodes().getLength() == 1 && "".equals(node.getChildNodes().item(0).getTextContent()) &&
                        (node.getChildNodes().item(0).getAttributes() == null || node.getChildNodes().item(0).getAttributes().getLength() == 0)) {

                } else {
                    isSon = true;
                }
            }
            boolean textChild = isTextChild(node);

            if(textChild) {
                tempMap.put(node.getNodeName(), node.getTextContent());
                continue;
            } else {
                tempMap = setAttributes(node, tempMap);
            }
            list.add(tempMap);
            if(isSon) {
                recursion(node, tempMap);
            }
        }
    }
    private static void recursion(Node node, Map tempMap){
        // 若包含子节点，则递归遍历
        Map map1 = new HashMap();
        int listIndex = findRepetition(node);
        boolean isTempList = false;
        if (listIndex != -1) {
            isTempList = true;
        }
//        if (isTempList) {
//            System.out.println(node.getNodeName() + ":" + isTempList);
//        }
        String nodeName = node.getNodeName();

//                Map<String, Object> parentMapTmp = tempMap;

        if (isTempList) {
//            String listName = node.getChildNodes().item(listIndex).getNodeName();
//            if (listName != null && node.getAttributes().getLength() > 0) {
//                nodeName = listName;
//            }
            List arrayList = new ArrayList();

            tempMap.put(nodeName, arrayList);
            searchAndShow(node, arrayList);
//                    searchAndShow(node.getChildNodes(), arrayList, parentMapTmp, isTempList);
        } else {
//                    parentMapTmp.put(nodeName, map1);
            tempMap.put(nodeName, map1);
            searchAndShow(node, map1);
        }
    }
    public static Map setAttributes(Node node, Map map){
        Map<String, Object> tempMap = null;
        NamedNodeMap attributes = node.getAttributes();
        if(attributes != null && attributes.getLength() > 0) {
            tempMap = new HashMap();
            map.put(node.getNodeName(), tempMap);
            for (int j = 0; j < attributes.getLength(); j++) {
                Node item = attributes.item(j);
                if(tempMap != null) {
                    tempMap.put(item.getNodeName(), item.getNodeValue());
                } else {
                    map.put(item.getNodeName(), item.getNodeValue());
                }
            }
        }
        if(tempMap != null) {
            return tempMap;
        }
        return map;
    }

    public static boolean isTextChild(Node node) {
        if(node.getChildNodes().getLength() == 1) {
            if (node.getChildNodes().item(0) instanceof DeferredTextImpl || node.getChildNodes().item(0) instanceof DeferredCDATASectionImpl) {
                if (!node.getTextContent().trim().equals("")
                        && node.getTextContent() != null) {
                    return true;
                }
            }
        }
        if (node.getChildNodes().getLength() > 0) {
            if(node.getChildNodes().getLength() == 1 && "".equals(node.getChildNodes().item(0).getTextContent()) &&
                    (node.getChildNodes().item(0).getAttributes() == null || node.getChildNodes().item(0).getAttributes().getLength() == 0)) {
                if (!node.getTextContent().trim().equals("")
                        && node.getTextContent() != null) {
                    return true;
                }

            }
        } else {
            // 若不包含子节点，则输出节点中的内容
            if (!node.getTextContent().trim().equals("")
                    && node.getTextContent() != null) {
                return true;
            }
        }
        return false;
    }
    /**
     * 查找是否有重复
     * @param node
     * @return -1 没有重复,其他重复第一个的index
     */
    public static int findRepetition(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            for (int j = 0; j < nodeList.getLength(); j++) {

                if(!nodeList.item(i).getNodeName().equals("#text") && i != j && nodeList.item(i).getNodeName().equals(nodeList.item(j).getNodeName())) {
//                    System.out.println("node1:" + nodeList.item(i).getNodeName() + ";node2:" + nodeList.item(j).getNodeName());
                    return i;
                }
            }
        }
        return -1;
    }
}
