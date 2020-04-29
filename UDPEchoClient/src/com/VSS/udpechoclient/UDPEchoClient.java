package com.VSS.udpechoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UDPEchoClient {
    static final int port = 4711;
    public static void main(String[] args) throws IOException {
        try {
            InetAddress servAddr = InetAddress.getByName("localhost");
            DatagramSocket client = new DatagramSocket();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter string to echo");
            while (true) {
                String msg = br.readLine();
                byte[] data = msg.getBytes();
                DatagramPacket clientPacket = new DatagramPacket(data, data.length, servAddr, port);
                client.send(clientPacket);

                client.receive(clientPacket);
                data = clientPacket.getData();
                msg = new String(data);
                System.out.println("Received " + msg + " from Server");
                if (msg.equals("exit")) {
                    break;
                }
            }
            client.close();
            System.out.println("Shutting down");
        }
        catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
