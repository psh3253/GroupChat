package chat.server.network;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunnable implements Runnable {
    private final JTextArea content;

    private final JLabel concurrentUserLabel;

    public ServerRunnable(JTextArea content, JLabel concurrentUserLabel) {
        this.content = content;
        this.concurrentUserLabel = concurrentUserLabel;
    }

    @Override
    public void run() {
        ServerSocket listener;
        Socket socket;
        try {
            listener = new ServerSocket(9999);
            while (true) {
                socket = listener.accept();
                Thread thread = new Thread(new ClientManager(socket, content, concurrentUserLabel));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
