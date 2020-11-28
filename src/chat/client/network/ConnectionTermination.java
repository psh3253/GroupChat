package chat.client.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTermination {
    private static ConnectionTermination instance = null;   // 인스턴스

    // 인스턴스화된 ConnectionTermination 객체 반환 함수
    public static ConnectionTermination getInstance() {
        if (instance == null)
            instance = new ConnectionTermination();
        return instance;
    }

    // 서버 연결 종료 함수
    public void disconnect() {
        Socket socket;
        ObjectOutputStream out;

        String[] disconnectObject = new String[2];
        disconnectObject[0] = "disconnect"; // disconnect 헤더
        disconnectObject[1] = ConnectionInfo.getInstance().getId(); // 아이디
        try {
            socket = ConnectionInfo.getInstance().getSocket();  // 소켓 가져오기
            out = ConnectionInfo.getInstance().getOut();    // 출력 스트림 가져오기
            out.writeObject(disconnectObject);  // 출력 스트림에 disconnectObject 내보내기
            out.flush();    // 전송
            socket.close(); // 소켓 닫기
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
