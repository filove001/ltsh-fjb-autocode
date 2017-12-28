package org.ltsh.generate.templete;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fengjianbo on 2016/3/29 0029.
 */
public class TempleteFileUtils {
    public static void writeFile(String templeteStr, String fileName, String baseOutPath) throws IOException {
        String path = baseOutPath;
        File mkdirsFile = new File(path);
        mkdirsFile.mkdirs();
        File file = new File(path + "/" + fileName);
        if(file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        fileOutputStream.write(templeteStr.getBytes("utf-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(templeteStr.getBytes("utf-8"));
        outputStream.writeTo(fileOutputStream);
        outputStream.flush();
        outputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
