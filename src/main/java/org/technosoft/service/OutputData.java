package org.technosoft.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputData {

    private static final File CURRENT_IPS = new File("src/main/resources/ips.csv");

    public static File getCurrentIps() {
        return CURRENT_IPS;
    }

    private boolean createFile(File file) throws IOException {
        return file.createNewFile();
    }

    public static void appendFile(String ipPlusPort) {
        try (var bufferedWriter = new BufferedWriter(new FileWriter(CURRENT_IPS, true))) {
            bufferedWriter.write(ipPlusPort + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
