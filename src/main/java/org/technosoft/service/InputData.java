package org.technosoft.service;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputData {

    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public String inputData() {
        var ipAddress = "";
        System.out.println("Insert ip address:");
        while (true) {
            ipAddress = scanner.nextLine();
            if (!isValidIPV4(ipAddress)) {
                System.out.println("You insert invalid data. Try again.");
            } else {
                System.out.println("""
                        It's correct. We starting scanning. It's take a time.
                        Be patient... min 15 - 20 or so...""");
                break;
            }
        }
        return ipAddress;
    }

    public String getFirstIpInTheNetwork(String ipAddress) {
        String[] strings = ipAddress.split("\\.");
        strings[3] = "0";
        return String.join(".", strings);
    }

    private boolean isValidIPV4(final String s) {
        var IPV4_REGEX =
                "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
        var IPV4_PATTERN = Pattern.compile(IPV4_REGEX);
        return IPV4_PATTERN.matcher(s).matches();
    }
}

