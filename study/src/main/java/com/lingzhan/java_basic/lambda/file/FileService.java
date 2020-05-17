package com.lingzhan.java_basic.lambda.file;

import java.io.*;

/**
 * Created by 凌战 on 2019/11/25
 */
public class FileService {

    public void fileHandle(String url, FileConsumer fileConsumer) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        //循环读取文件内容
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        fileConsumer.fileHandler(stringBuilder.toString());

    }
}
