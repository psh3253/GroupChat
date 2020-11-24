package chat.client.message;

import chat.client.network.ConnectionInfo;

import javax.swing.*;
import java.io.*;

public class MessageSender {
    private static MessageSender instance = null;

    public static MessageSender getInstance() {
        if (instance == null)
            instance = new MessageSender();
        return instance;
    }

    public void sendMessage(String message) {
        ObjectOutputStream out;

        String[] messageObject = new String[3];
        messageObject[0] = "message";
        messageObject[1] = ConnectionInfo.getInstance().getId();
        messageObject[2] = message;
        try {
            out = ConnectionInfo.getInstance().getOut();
            out.writeObject(messageObject);
            out.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "메시지 전송에 실패하였습니다.", "전송 실패", JOptionPane.WARNING_MESSAGE);
        }
    }
}
