package org.technosoft.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PortScanner {
    private static final int THREADS = Runtime.getRuntime().availableProcessors();

    public static List<Integer> portScan(String ip) {

        ConcurrentLinkedQueue<Integer> openPorts = new ConcurrentLinkedQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
        AtomicInteger port = new AtomicInteger(0);
        while (port.get() < 65535) {
            final int currentPort = port.getAndIncrement();
            executorService.submit(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, currentPort), 50);
                    socket.close();
                    openPorts.add(currentPort);
                    System.out.println(ip + " port open: " + currentPort);
                } catch (IOException ignored) {
                }

            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(6, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Integer> openPortList = new ArrayList<>();
        while (!openPorts.isEmpty()) {
            openPortList.add(openPorts.poll());
        }
        return openPortList;
    }
}
