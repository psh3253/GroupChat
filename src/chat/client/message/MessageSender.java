package chat.client.message;

import chat.client.network.ConnectionInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageSender {
    private static MessageSender instance = null;   // 인스턴스

    // 인스턴스화된 MessageSender 객체 반환 함수
    public static MessageSender getInstance() {
        if (instance == null)
            instance = new MessageSender();
        return instance;
    }

    // 메시지 전송 함수
    public void sendMessage(String message) {
        ObjectOutputStream out;

        String[] messageObject = new String[3];
        messageObject[0] = "message";   // message 헤더
        messageObject[1] = ConnectionInfo.getInstance().getId();    // id
        messageObject[2] = message; // 메시지 내용
        try {
            out = ConnectionInfo.getInstance().getOut();    // 출력 스트림 가져오기
            out.writeObject(messageObject); // 출력 스트림에 messageObject 내보내기
            out.flush();    // 전송
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "메시지 전송에 실패하였습니다.", "전송 실패", JOptionPane.WARNING_MESSAGE);
        }
    }
}
