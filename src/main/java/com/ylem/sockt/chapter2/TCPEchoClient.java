package com.ylem.sockt.chapter2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class TCPEchoClient {
    public static void main(String[] args) throws IOException {
//        if ((args.length < 2) || (args.length > 3)) {
//            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
//        }
//
//        // server name or ip address
//        String server = args[0];
//        byte[] data = args[1].getBytes();
//
//        int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        String server = "127.0.0.1";
        int serverPort = 8080;

        Socket socket = new Socket(server, serverPort);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.nextLine();

            byte[] data = msg.getBytes();

            out.write(data);

            int totalByteRcvd = 0;
            int byteRcvd;
            while (totalByteRcvd < data.length) {
                if ((byteRcvd = in.read(data, totalByteRcvd, data.length - totalByteRcvd)) == -1) {
                    throw new SocketException("Connection closed prematurely");
                }
                totalByteRcvd += byteRcvd;
            }
            System.out.println("Received: " + new String(data));

        }
        socket.close();
    }
}
