package chat.client.message;

import chat.client.network.ConnectionInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

public class MessageReceiver implements Runnable {
    private final JTextArea content;    // 채팅이 표시되는 텍스트 영역

    private final JLabel concurrentUserLabel;   // 동시 접속자수 표시 레이블

    public MessageReceiver(JTextArea content, JLabel concurrentUserLabel) {
        this.content = content;
        this.concurrentUserLabel = concurrentUserLabel;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        String[] messageObject;
        try {
            in = ConnectionInfo.getInstance().getIn();  // 입력 스트림
            while (true) {
                messageObject = (String[]) in.readObject(); // 입력 스트림으로 객체를 읽어오기
                if (messageObject[0].equals("message")) {   // 헤더가 message 인 경우
                    content.append(messageObject[1] + "  >> " + messageObject[2] + "\n");
                } else if (messageObject[0].equals("concurrentUserCount")) {  // 헤더가 concurrentUserCount 인 경우
                    concurrentUserLabel.setText("동시 접속자수 : " + messageObject[1] + "명");
                } else if (messageObject[0].equals("join")) { // 헤더가 join 인 경우
                    content.append(messageObject[1] + "님이 입장하셨습니다.\n");
                } else if (messageObject[0].equals("quit")) { // 헤더가 quit 인 경우
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
