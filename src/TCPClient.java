
public class TCPClient {
    public static void main(String[] args) {
        String hostName = args[0];
        int port = Integer.parseInt(args[1]);
        new ClientData(hostName, port);
    }
}