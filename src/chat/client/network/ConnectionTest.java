package chat.client.network;

import java.io.*;
import java.net.Socket;

public class ConnectionTest {
    private static ConnectionTest instance = null;

    public static ConnectionTest getInstance() {
        if (instance == null)
            instance = new ConnectionTest();
        return instance;
    }

    public boolean PingPong() {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        String[] testObject = new String[2];
        testObject[0] = "connectionTest";
        testObject[1] = "ping";
        try {
            socket = new Socket(ConnectionInfo.getInstance().getAddress(), ConnectionInfo.getInstance().getPort());
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            ConnectionInfo.getInstance().setIn(in);
            ConnectionInfo.getInstance().setOut(out);
            out.writeObject(testObject);
            out.flush();
            while (true) {
                String[] response = (String[]) in.readObject();
                if (response[0].equals("connectionTest")) {
                    if (response[1].equals("pong")) {
                        ConnectionInfo.getInstance().setSocket(socket);
                        return true;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.getStackTrace();
            return false;
        }
    }
}
