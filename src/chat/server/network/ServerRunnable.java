package chat.server.network;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunnable implements Runnable {
    private final JTextArea content;    // 채팅이 표시되는 텍스트 영역

    private final JLabel concurrentUserLabel;   // 동시 접속자수 표시 레이블

    public ServerRunnable(JTextArea content, JLabel concurrentUserLabel) {
        this.content = content;
        this.concurrentUserLabel = concurrentUserLabel;
    }

    @Override
    public void run() {
        ServerSocket listener;
        Socket socket;
        try {
            // 서버 소켓 생성
            listener = new ServerSocket(9999);
            while (true) {
                socket = listener.accept(); // 클라이언트 접속 요청 대기

                // 클라이언트가 접속하면 ClientManager Thread 생성 및 시작
                Thread thread = new Thread(new ClientManager(socket, content, concurrentUserLabel));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
