package ru.geekbrains.lesson5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BackupFiles {

    public static void main(String[] args) {
        backupFiles(new File("."));
    }

    static void backupFiles(File sourceDir) {
        File backupDir = new File("./backupedFiles");

        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        File[] files = sourceDir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                try {
                    Path sourcePath = file.toPath();
                    Path destinationPath = new File(backupDir, file.getName()).toPath();
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copied: " + file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
