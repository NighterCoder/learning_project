package com.lingzhan.java_basic.lambda.file;

/**
 * Created by 凌战 on 2019/11/25
 */
@FunctionalInterface
public interface FileConsumer {

    void fileHandler(String fileContent);
}
