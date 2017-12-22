import com.google.gson.Gson;
import org.apache.commons.jexl3.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.ltsh.autocode.dao.BaseDao;
import org.ltsh.autocode.util.DataUtil;
import org.ltsh.autocode.util.JexlUtils;
import org.ltsh.autocode.util.TempleteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;



import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fengjb-it on 2016/2/14 0014.
 */
public class StartUp {
    private static Logger log = LoggerFactory.getLogger(StartUp.class);
    String str = "";
    @Test
    public void createChat() throws Exception {
        String templetePath = TempleteUtils.class.getClass().getResource("/templete/ltsh-chat").getPath();
//        TempleteUtils.writeModules("user_group", templetePath,
//                "com.ltsh.chat.service",
//                "D:\\test\\xiangmu\\chat\\");
        String tableName = "user_group";
        String basePackageStr = "com.ltsh.chat.service";
        String outPath = "D:\\test\\xiangmu\\chat\\";
        Map<String, Object> map = new HashMap<String, Object>();
//        TempleteUtils.writeAll(tableName, templetePath, basePackageStr, outPath, "sql");
        TempleteUtils.writeFileForPage(tableName, templetePath, basePackageStr, "entity", outPath+"\\java", DataUtil.getMethodName(tableName) + ".java", "entity.tempc", map);
        TempleteUtils.writeFileForPage(tableName, templetePath, basePackageStr, "dao", outPath + "\\java", DataUtil.getMethodName(tableName) + "Dao.java", "dao.temp", map);
        TempleteUtils.writeFile(tableName, templetePath, basePackageStr, "sqlmaps", outPath + "\\resources", "sql", DataUtil.getMethodName(tableName) + ".sql", "sql.temp", map);
    }
    @Test
    public void writeAll() throws Exception {
        String templetePath = TempleteUtils.class.getClass().getResource("/templete/demo").getPath();
//        TempleteUtils.writeModules("user_group", templetePath,
//                "com.ltsh.chat.service",
//                "D:\\test\\xiangmu\\chat\\");
        String tableName = "user_group";
        String basePackageStr = "com.ltsh.chat.service";
        String outPath = "D:\\test\\xiangmu\\demo\\";
        Map<String, Object> map = new HashMap<String, Object>();
        TempleteUtils.writeAll(tableName, templetePath, basePackageStr, outPath, "sqlmaps");

    }
//    public static void main(String[] args) throws Exception {
//        String templetePath = TempleteUtils.class.getClass().getResource("/templete/demo").getPath();
//        TempleteUtils.writeModules("user_group", templetePath,
//                "com.ltsh.chat.service",
//                "D:\\test\\xiangmu\\");
//
//    }

}
