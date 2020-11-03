import java.io.*;
import java.net.*;

class ServerData extends Thread {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private StringBuilder name;
    private String nick = "user" + TCPServer.map.size();

    public ServerData(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        start();
    }

    @Override
    public void run() {
        String fromUser;
        try {
            fromUser = in.readLine();
            out.println(fromUser);
            name = new StringBuilder(fromUser);
            makeName(name);
            TCPServer.map.remove(nick);
            TCPServer.map.put(name.toString(), this);
            try {
                while (true) {
                    fromUser = in.readLine();
                    if (fromUser.equals("@bye")) {
                        closeAll();
                        break;
                    }
                    if (fromUser.contains("@show")) {
                        TCPServer.map.get(name.toString()).out.println(TCPServer.vedomost);
                        continue;
                    }
                    if (fromUser.contains("@set")) {
                        setGrade(fromUser);
                        continue;
                    }
                    if (fromUser.contains("@senduser")) {
                        StringBuilder nameToSend = new StringBuilder(fromUser);
                        makeNameToSend(nameToSend);
                        TCPServer.map.get(nameToSend.toString())
                                .out.println("From " + name + ": " + fromUser.substring(18 + name.length() + nameToSend.length()));
                        continue;
                    }
                    for (ServerData iter : TCPServer.map.values()) {
                        if(!iter.name.equals(name)) {
                            iter.out.println(fromUser);
                        }
                    }
                }
            } catch (Exception e) {}
        } catch (IOException e) {
            closeAll();
        }
    }

    private void setGrade(String fromUser) {
        //From artem: @set name ex_num grade
        var mas = fromUser.split(" ");
        String ved_name;
        int row = -1;
        for (int i = 1; i < TCPServer.vedomost.getRow(); i++) {
            ved_name = TCPServer.vedomost.getElement(i, 0);
            if (ved_name.equals(mas[3])) {
                row = i;
                break;
            }
        }
        if (row != -1 && Integer.parseInt(mas[4]) > 0 && Integer.parseInt(mas[4]) < TCPServer.vedomost.getColumn()) {
            TCPServer.vedomost.setElement(row, Integer.parseInt(mas[4]), mas[5]);
        }
    }

    private void makeName(StringBuilder name) {
        //artem connected
        int ind = name.toString().indexOf(' ');
        name.delete(ind, name.length());
    }

    private void makeNameToSend(StringBuilder name) {
        //From artem: @senduser egor message
        var mas = name.toString().split(" ");
        name.delete(0, name.length());
        name.append(mas[3]);
    }

    private void closeAll() {
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (Exception e) {}
    }
}