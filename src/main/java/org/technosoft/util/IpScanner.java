package org.technosoft.util;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class IpScanner {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private final static int HOSTS_AMOUNT = 256;

    public static ConcurrentSkipListSet<String> scan(String firstIpInTheNetwork) {
        var executorService = Executors.newFixedThreadPool(THREADS);
        var ipsSet = new ConcurrentSkipListSet<String>();
        AtomicInteger ips = new AtomicInteger(0);
        while (ips.get() <= HOSTS_AMOUNT) {
            String ip = firstIpInTheNetwork + ips.getAndIncrement();
            executorService.submit(() -> {
                try {
                    InetAddress inAddress = InetAddress.getByName(ip);
                    if (inAddress.isReachable(500)) {
                        System.out.println("found ip: " + ip);
                        ipsSet.add(ip);
                    }
                } catch (IOException ignored) {
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return ipsSet;
    }
}
