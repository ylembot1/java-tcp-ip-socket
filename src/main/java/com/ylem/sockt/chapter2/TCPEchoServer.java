package com.ylem.sockt.chapter2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPEchoServer {
    private static final int BUFSIZE = 32;

    public static void main(String[] args) throws IOException {
//        if (args.length != 1) {
//            throw new IllegalArgumentException("Parameter(s): <Port>");
//        }
//
//        int serverPort = Integer.parseInt(args[0]);

        int serverPort = 8080;

        ServerSocket serverSocket = new ServerSocket(serverPort);

        int receiveSize;
        byte[] receiveBuf = new byte[BUFSIZE];

        while (true) {
            Socket clntSock = serverSocket.accept();

            SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
            System.out.println("client Address: " + clientAddress);

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            while ((receiveSize = in.read(receiveBuf)) != -1) {
                out.write(receiveBuf);
            }

            clntSock.close();
        }
    }
}
