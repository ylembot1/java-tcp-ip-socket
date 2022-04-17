package com.ylem.sockt.chapter2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPEchoServer {
    private static final int ECHOMAX = 255;

    public static void main(String[] args) throws IOException {

        int serverPort = 8080;

        DatagramSocket socket = new DatagramSocket(serverPort);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
        packet.getData();

        while (true) {
            socket.receive(packet);
            System.out.println("Handling client at " + packet.getAddress().getHostAddress()
                    + " on port " + packet.getPort());

            // 打印client中的内容
            byte[] data = Arrays.copyOfRange(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(new String(data));

            socket.send(packet);
            packet.setLength(ECHOMAX);
        }
    }
}
