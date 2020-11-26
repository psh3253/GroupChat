package chat.client.message;

import chat.client.network.ConnectionInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class MessageReceiver implements Runnable {
    private final Socket socket;
    private final JTextArea content;
    private final JLabel concurrentUserLabel;

    public MessageReceiver(JTextArea content, JLabel concurrentUserLabel) {
        this.socket = ConnectionInfo.getInstance().getSocket();
        this.content = content;
        this.concurrentUserLabel = concurrentUserLabel;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        ObjectOutputStream out;
        String[] messageObject;
        try {
            in = ConnectionInfo.getInstance().getIn();
            out = ConnectionInfo.getInstance().getOut();
            while (true) {
                messageObject = (String[]) in.readObject();
                System.out.println(Arrays.toString(messageObject));
                if (messageObject[0].equals("message")) {
                    content.append(messageObject[1] + "  >> " + messageObject[2] + "\n");
                } else if (messageObject[0].equals("concurrentUserCount")) {
                    concurrentUserLabel.setText("동시 접속자수 : " + messageObject[1] + "명");
                } else if (messageObject[0].equals("join")) {
                    content.append(messageObject[1] + "님이 입장하셨습니다.\n");
                } else if (messageObject[0].equals("quit")) {
                    content.append(messageObject[1] + "님이 퇴장하셨습니다.\n");
                }
            }
        } catch (SocketException e) {
            //e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
