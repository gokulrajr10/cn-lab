import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class Client {

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java Client <hostname> <port> <filepath>");
            System.exit(1);
        }
        double sendingDelay = 0;
        long startTime = System.currentTimeMillis();
        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getByName(args[0]);
            int port = Integer.valueOf(args[1]);
            try(BufferedReader in = new BufferedReader(new FileReader(args[2]))){
                String value;
                while ((value = in.readLine())!=null) {
                    byte[] buf = value.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                    boolean retransmit;
                    do {
                        long timeBeforeSend = System.currentTimeMillis();
                        System.out.println("Trying to send below message in a packet: \n" + value);
                        socket.send(packet);
                        // calculating the time required for pushing all bits of datagram into the channel
                        sendingDelay += System.currentTimeMillis() - timeBeforeSend;
                        byte[] receiveBuf = new byte[256];
                        // packet for receiving response from server
                        DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                        // waiting for the response from server
                        socket.receive(receivePacket);
                        String received = new String(receivePacket.getData(), 0 , receivePacket.getLength());
                        // checking the server response
                        if(received.equals("NACK")){
                            System.out.println("NACK received: retransmitting");
                            retransmit = true;
                        } else if(received.equals("ACK")){
                            System.out.println("ACK received: successfully transmitted ");
                            retransmit = false;
                        } else {
                            System.err.println("can't identify the received message: " + received);
                            return;
                        }
                    } while (retransmit);
                    System.out.println("transmission of current packet is complete...");
                }
                System.out.println("Closing Datagram socket ...");
            } catch(FileNotFoundException ex){
                System.err.println("Couldn't open the file " + args[2] + ". Please Check the path");
                return;
            }
            long totalDelay = System.currentTimeMillis() - startTime;
            double linkUtilization = sendingDelay / totalDelay;
            System.out.println("total sending delay is: " + sendingDelay + "ms");
            System.out.println("total delay is: " + totalDelay + "ms");
            System.out.println("link utilization is: " + linkUtilization);
        }
    }
}