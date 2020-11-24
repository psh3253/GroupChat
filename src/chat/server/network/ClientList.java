package chat.server.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientList {

    private String id;

    private Socket socket;

    private ObjectInputStream in;

    private ObjectOutputStream out;

    public ClientList(String id, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.id = id;
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
}
