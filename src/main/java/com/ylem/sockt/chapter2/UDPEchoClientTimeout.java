package com.ylem.sockt.chapter2;

import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UDPEchoClientTimeout {

    private static final int TIMEOUT = 3000;
    private static final int MAXTRIES = 5;

    public static void main(String[] args) throws IOException {

        String sendStr = "aiyouwei";

        InetAddress serverAddress = InetAddress.getByName("127.0.0.1");

        System.out.println(serverAddress.getHostName());
        System.out.println(serverAddress.getHostAddress());

        byte[] byteToSend = sendStr.getBytes();

        int serverPort = Integer.parseInt("8080");

        DatagramSocket socket = new DatagramSocket();

        socket.setSoTimeout(TIMEOUT);

        DatagramPacket sendPacket = new DatagramPacket(byteToSend,
                byteToSend.length,
                serverAddress,
                serverPort);

        DatagramPacket receivePacket = new DatagramPacket(new byte[byteToSend.length], byteToSend.length);

        int tries = 0;
        boolean receiveResponse = false;
        do {
            socket.send(sendPacket);
            try {
                socket.receive(receivePacket);

                if (!receivePacket.getAddress().equals(serverAddress)) {
                    throw new IOException("Received packet from an unknown source");
                }
                receiveResponse = true;
            } catch (InterruptedIOException e) {
                tries += 1;
                System.out.println("Timed out, " + (MAXTRIES - tries) + "more tries...");
            }
        } while ((!receiveResponse) && (tries < MAXTRIES));

        if (receiveResponse) {
            System.out.println("Received: " + new String(receivePacket.getData()));
        } else {
            System.out.println("No response -- giving up");
        }
        socket.close();
    }
}
