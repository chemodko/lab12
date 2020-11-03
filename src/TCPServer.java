import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {

    public static Map<String, ServerData> map = new HashMap<String, ServerData>();
    public static Vedomost vedomost;

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Hello from TCPServer!");

        fillVedomost();

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                try {
                    map.put("user" + map.size(), new ServerData(clientSocket));
                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }

    private static void fillVedomost() throws FileNotFoundException {
        int count = 1;
        try(Scanner sc = new Scanner(new FileInputStream("vedom.txt"))) {
            sc.nextLine();
       
            while (sc.hasNextLine()) {
                count++;
                sc.nextLine();
            }
            vedomost = new Vedomost(count);
        }
    
        try (Scanner sc = new Scanner(new FileInputStream("vedom.txt"))) {
            for (int i = 0; i < count; i++) {
                String lines = sc.nextLine();
                String[] data = lines.split(" ");
                for (int j = 0; j < 6; j++) {
                    vedomost.setElement(i, j, data[j]);
                }
            }
        }
    }
}