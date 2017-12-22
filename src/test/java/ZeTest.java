import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fengjb-it on 2016/2/15 0015.
 */
public class ZeTest {
    public static void main(String[] args) {
        test2();


    }

    @Test
    public void test4(){
        String regex = ".*?<templete[^>]*>((.|\\n)*?)<\\/templete>.*?";
        String str = "<templete id=\"1\" dataSource=\"columnDatas\" type=\"for\">\n" +
                "<id column=\"${column_name}\" property=\"${propertyName}\" />\n" +
                "</templete>";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        while(matcher.find()) {
            String group = matcher.group();
//            group = group.substring(2, group.length()-1);
            System.out.println(group);
        }
    }
    @Test
    public void test3(){
        String regex = "\\$\\{[^}]*\\}";
        String str = "<templete>\n" +
                "        <for list=\"datas\" item=\"property\">\n" +
                "            /**\n" +
                "             * ${property.describe}\n" +
                "             **/\n" +
                "            private ${property.dataType} ${property.propertyName};\n" +
                "            public void set${property.propertyNameUpper}(${property.dataType} ${property.propertyName}) {\n" +
                "                this.${property.propertyName} = ${property.propertyName};\n" +
                "            }\n" +
                "            public ${item.dataType} get${item.propertyNameUpper}(){\n" +
                "                return ${item.propertyName};\n" +
                "            }\n" +
                "        </for>\n" +
                "    </templete>";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        while(matcher.find()) {
            String group = matcher.group();
            group = group.substring(2, group.length()-1);
            System.out.println(group);
        }
    }
    private static void test2(){
        String regex = "\\$\\{columnDatas[^{]*\\}";
        String str = "private ${columnDatas.dataType} ${columnDatas.propertyName};\n";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher1 = compile.matcher(str);
        System.out.println(matcher1.find());
        System.out.println(matcher1.group());
        System.out.println(matcher1.find());
        System.out.println(matcher1.group());
    }
    private static void test1(){

//        <templete.*>(.|\n)*</templete>
//        <templete[^>]*>
        String str = "<templete>\n" +
                "        <for list=\"datas\" item=\"property\">\n" +
                "            /**\n" +
                "             * ${property.describe}\n" +
                "             **/\n" +
                "            private ${property.dataType} ${property.propertyName};\n" +
                "            public void set${property.propertyNameUpper}(${property.dataType} ${property.propertyName}) {\n" +
                "                this.${property.propertyName} = ${property.propertyName};\n" +
                "            }\n" +
                "            public ${item.dataType} get${item.propertyNameUpper}(){\n" +
                "                return ${item.propertyName};\n" +
                "            }\n" +
                "        </for>\n" +
                "    </templete>";

        String regex = "<templete[^>]*>|</templete>";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher1 = compile.matcher(str);
        System.out.println(matcher1.replaceAll(""));
        String s = matcher1.replaceAll("");
        regex = "<for[^>]*>|</for>";
        compile = Pattern.compile(regex);
        matcher1 = compile.matcher(s);
        String templeteStr = matcher1.replaceAll("");

    }
}
