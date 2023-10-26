package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
//        File filePath = new File(".\\.gitignore");
//        try {
//            System.out.println(filePath.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//        File dir = new File(".\\src\\com\\basejava\\webapp");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//
//        if (list != null) {
//            for (String name :
//                    list) {
//                System.out.println(name);
//            }
//        }


//        try(FileInputStream fis = new FileInputStream(filePath)){
//            System.out.println(fis.read());
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
        File dir2 = new File(".");
        try {
            searchFile(dir2, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void searchFile(File dir, String indent) throws IOException {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file :
                    files) {
                if (file.isDirectory()) {
                    System.out.println(indent + file.getName());
                    searchFile(file, indent + "  ");
                } else if (file.isFile()) {
                    System.out.println(indent + file.getName());
                }

            }
        }
    }
}
