/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.lone64.launcher.occupationalwar.util;

import dev.lone64.launcher.occupationalwar.Main;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author lone64dev
 */
public class FileUtil {
    public static boolean createFile(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
                return true;
            }
            return false;
        } catch (final IOException e) {
            System.out.print("[%s] %s\n".formatted(Main.LAUNCHER_NAME, e.getMessage()));
            return false;
        }
    }

    public static boolean createDirectory(File file) {
        if (!file.exists()) {
            file.mkdir();
            return true;
        }
        return false;
    }

    public static boolean deleteFile(File file) {
        try {
            if (file.exists()) {
                FileUtils.delete(file);
            }
            return false;
        } catch (final IOException e) {
            System.out.print("[%s] %s\n".formatted(Main.LAUNCHER_NAME, e.getMessage()));
            return false;
        }
    }

    public static boolean deleteDirectory(File file) {
        try {
            if (file.exists()) {
                FileUtils.deleteDirectory(file);
            }
            return false;
        } catch (final IOException e) {
            System.out.print("[%s] %s\n".formatted(Main.LAUNCHER_NAME, e.getMessage()));
            return false;
        }
    }

    public static boolean downloadFile(String url, File file, boolean replaced) {
        try {
            if (replaced) {
                FileUtils.copyURLToFile(new URL(url), file);
                return true;
            }

            if (!file.exists()) {
                FileUtils.copyURLToFile(new URL(url), file);
                return true;
            }
            return false;
        } catch (IOException e) {
            System.out.print("[%s] %s\n".formatted(Main.LAUNCHER_NAME, e.getMessage()));
            return false;
        }
    }

    public static boolean unzip(String s, String path) {
        try {
            var wrapper = new UnzipUtil();
            wrapper.unzip(s, path);
            return true;
        } catch (IOException e) {
            System.out.print("[%s] %s\n".formatted(Main.LAUNCHER_NAME, e.getMessage()));
            return false;
        }
    }
    
    public static File getDataFolder() {
        return new File("C:/Users/%s/AppData/Roaming/%s".formatted(System.getProperty("user.name"), Main.LAUNCHER_PATH));
    }

    public static File getDataFolder(String path) {
        return new File("C:/Users/%s/AppData/Roaming/%s/%s".formatted(System.getProperty("user.name"), Main.LAUNCHER_PATH, path));
    }
    
    public static List<String> getFileTags(String path) {
        var folder = getDataFolder(path);
        if (folder.exists()) {
            var fileTags = List.of(folder.list());
            if (!fileTags.isEmpty()) {
                return fileTags;
            }
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}