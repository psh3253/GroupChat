package chat.client.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTermination {
    private static ConnectionTermination instance = null;

    public static ConnectionTermination getInstance() {
        if (instance == null)
            instance = new ConnectionTermination();
        return instance;
    }

    public void disconnect()
    {
        Socket socket;
        ObjectOutputStream out;

        String[] disconnectObject = new String[1];
        disconnectObject[0] = "disconnect";
        try {
            socket = ConnectionInfo.getInstance().getSocket();
            out = ConnectionInfo.getInstance().getOut();
            out.writeObject(disconnectObject);
            out.flush();
            socket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
