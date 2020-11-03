import java.net.*;
import java.io.*;

class ClientData {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader sdtIn;
    private String name;
    private OutputThread output;
    private InputThread input;

    public ClientData(String hostName, int port) {
        try {
            clientSocket = new Socket(hostName, port);
            sdtIn = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.print("Input ypur name pls: ");
            try {
                name = sdtIn.readLine();
                out.println(name + " connected");
            } catch (IOException e) {
                e.printStackTrace();
            }

            output = new OutputThread();
            output.start();
            input = new InputThread();
            input.start();
        } catch (IOException e) {
            closeAll();
        }
    }

    private void closeAll() {
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (Exception e) {
        }
    }

    private class OutputThread extends Thread {
        @Override
        public void run() {
            String fromServer;
            try {
                while (true) {
                    fromServer = in.readLine();
                    if (fromServer.equals("@bye")) {
                        closeAll();
                        break;
                    }
                    System.out.println(fromServer);
                }
            } catch (IOException e) {
                closeAll();
            }
        }
    }

    public class InputThread extends Thread {
        @Override
        public void run() {
            String fromUser;
            try {
                while (true) {
                    fromUser = sdtIn.readLine();
                    if (fromUser.equals("@bye")) {
                        out.println("@bye");
                        closeAll();
                        break;
                    } else {
                        out.println("From " + name + ": " + fromUser);
                    }
                }
            } catch (IOException e) {
                closeAll();
            }
        }
    }
}