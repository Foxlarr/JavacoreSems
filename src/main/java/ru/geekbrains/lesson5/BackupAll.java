package ru.geekbrains.lesson5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BackupAll {

    public static void main(String[] args) {
        File sourceDir = new File(".");
        File backupDir = new File(sourceDir, "backup");
        backupFiles(sourceDir, backupDir);
    }

    static void backupFiles(File sourceDir, File backupDir) {
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        File[] files = sourceDir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                // Исключаем backup из обработки (Да, даже если файл. На всякий.)
                if (!file.getAbsolutePath().equals(backupDir.getAbsolutePath())) {
                    try {
                        Path sourcePath = file.toPath();
                        Path destinationPath = new File(backupDir, file.getName()).toPath();
                        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Copied: " + file.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (file.isDirectory()) {
                // Исключаем backup из обработки
                if (!file.getAbsolutePath().equals(backupDir.getAbsolutePath())) {
                    backupFiles(file, new File(backupDir, file.getName()));
                }
            }
        }
    }
}
