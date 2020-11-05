package Homeworks.Lab6;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PathScan {


    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(".", "/");
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        System.out.println(scannedUrl);
        if (scannedUrl == null) {
            throw new IllegalArgumentException("Bad package " + scannedPackage);
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }


    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + "." + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            String className = resource.substring(0, resource.length() - 6);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}