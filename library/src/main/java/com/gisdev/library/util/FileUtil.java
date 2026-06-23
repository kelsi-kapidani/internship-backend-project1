package com.gisdev.library.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileUtil {

    public String generateStoredFileName(String originalName) {

        return System.currentTimeMillis()
                + "_"
                + originalName;
    }

    public String removeTimestampPrefix(String storedName) {

        int index = storedName.indexOf("_");

        if(index == -1) {
            return storedName;
        }

        return storedName.substring(index + 1);
    }
}
