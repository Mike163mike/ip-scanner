package org.technosoft;

import org.technosoft.service.InputData;
import org.technosoft.service.OutputData;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.technosoft.util.IpScanner.scan;
import static org.technosoft.util.PortScanner.portScan;

public class Main {

    /**
     * Источники "вдохновения":
     * <a href="https://www.theswdeveloper.com/post/create-a-port-scanner-with-java">...</a>
     * <a href="https://www.theswdeveloper.com/post/scan-for-devices-in-the-network-with-java">...</a>
     * <a href="https://stackoverflow.com/questions/5667371/validate-ipv4-address-in-java">...</a>
     */

    public static void main(String[] args) {

        var data = new InputData();
        var dataIn = data.inputData();
        var firstIpAddress = data.getFirstIpInTheNetwork(dataIn);

        ConcurrentSkipListSet<String> networkIps = scan(firstIpAddress);

        var listIpPlusPort = new ArrayList<String>();
        for (String ip : networkIps) {
            var ports = portScan(ip);
            for (Integer port : ports) {
                listIpPlusPort.add(String.join(":", ip, String.valueOf(port)));
            }
        }
        System.out.println("""
                If you want to save these ips with ports in CSV file, insert "Y",
                if don't - "N".""");
        while (true) {
            var scanner = InputData.getScanner();
            var select = scanner.next();
            if (select.equalsIgnoreCase("N")) {
                System.out.println("So long! It was nice to meet you!");
                break;
            } else if (select.equalsIgnoreCase("Y")) {
                listIpPlusPort.forEach(OutputData::appendFile);
                System.out.printf("""
                        You data saved in %s successfully, like this:
                        """, OutputData.getCurrentIps().getPath());
                listIpPlusPort.forEach(System.out::println);
                System.out.println("So long! It was nice to meet you!");
                break;
            }
            System.out.println("You input incorrect data. Try again.");
        }
    }
}

