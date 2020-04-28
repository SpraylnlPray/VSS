import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class EchoServerUDP {
    static final int port = 4711;
    static final int length = 256;

    public static void main(String[] args) throws IOException {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            byte[] buffer = new byte[length];
            DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
            System.out.println("Started udp server on port " + socket.getLocalPort());
            while(true) {
                inPacket.setLength(length);
                socket.receive(inPacket);
                byte[] data = inPacket.getData();
                String text = new String(data);
                System.out.println("Received " + text + " from client");
                if (data.length == 0) {
                    break;
                }
                InetAddress toAddr = inPacket.getAddress();
                int toPort = inPacket.getPort();
                DatagramPacket res = new DatagramPacket(data, data.length, toAddr, toPort);
                socket.send(res);
                if (text.equals("exit")) {
                    break;
                }
            }
            socket.close();
            System.out.println("Stopped");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
