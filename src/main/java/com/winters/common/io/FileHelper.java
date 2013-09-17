package com.winters.common.io;

import java.io.File;
import java.io.IOException;

/**
 * User: wentong.wang
 * Date: 13-9-17
 * Time: 下午11:57
 *
 */
public class FileHelper {

    public static String fixPath(String path) {
        if (path == null)
            return "";
        if (path.indexOf('\\') >= 0)
            path = path.replace('\\', '/');

        int len = path.length();
        if (len > 0 && path.charAt(len - 1) == '/') {
            path = path.substring(0, len - 1);
        }
        return path;
    }

    public static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf('.');
        if (index < 0 || index == filePath.length())
            return null;
        return filePath.substring(index + 1);
    }

    private static void deleteFileAndEmptyDirectory(File file) {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            String[] subfiles = file.list();
            if (subfiles == null || subfiles.length == 0) {
                file.delete();
                File parent = file.getParentFile();
                deleteFileAndEmptyDirectory(parent);
            }
        } else if (file.isFile()) {
            file.delete();
            File parent = file.getParentFile();
            deleteFileAndEmptyDirectory(parent);
        }
    }

    public static void deleteFileAndEmptyDirectory(String path) {
        deleteFileAndEmptyDirectory(new File(path));
    }

    public static File createPathAndFile(String path) throws IOException {
        if (path == null)
            return null;
        path = fixPath(path);
        int rootStart = path.indexOf("/");
        String rootPath = path.substring(0, rootStart);
        String subPath = path.substring(rootStart + 1);

        if (subPath.indexOf('/') < 0) {
            File file = new File(rootPath);
            file.createNewFile();
            return file;
        }
        String parts[] = subPath.split("/");
        String nowPath = rootPath;
        File file = null;
        for (int i = 0; i < parts.length - 1; i++) {
            nowPath += "/" + parts[i];
            file = new File(nowPath);
            if (file.isDirectory())
                continue;

            file.mkdir();
        }

        file = new File(path);
        file.createNewFile();
        return file;
    }
}
