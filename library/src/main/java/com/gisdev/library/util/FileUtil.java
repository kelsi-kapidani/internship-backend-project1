package com.gisdev.library.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FileUtil {

    public String generateStoredFileName(String originalName) {
        return new Random().nextLong(System.currentTimeMillis()) + "_" + originalName;
    }

    public String removeTimestampPrefix(String storedName) {
        int index = storedName.indexOf("_");
        if(index == -1) {
            return storedName;
        }
        return storedName.substring(index + 1);
    }
}
