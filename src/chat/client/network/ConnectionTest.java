package chat.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTest {
    private static ConnectionTest instance = null;  // 인스턴스

    // 인스턴스화된 ConnectionTest 객체 반환 함수
    public static ConnectionTest getInstance() {
        if (instance == null)
            instance = new ConnectionTest();
        return instance;
    }

    // 서버와 연결 테스트를 위한 핑퐁 함수
    public boolean PingPong() {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        String[] testObject = new String[2];
        testObject[0] = "connectionTest";   // connectionTest 헤더
        testObject[1] = "ping"; // 핑
        try {
            // 설정된 서버 주소와 포트를 가져와 서버에 연결
            socket = new Socket(ConnectionInfo.getInstance().getAddress(), ConnectionInfo.getInstance().getPort());
            in = new ObjectInputStream(socket.getInputStream());    // 입력 스트림 생성
            out = new ObjectOutputStream(socket.getOutputStream()); // 출력 스트림 생성
            out.writeObject(testObject);    // 출력 스트림에 testObject 내보내기
            out.flush();    // 전송
            while (true) {
                String[] response = (String[]) in.readObject(); // 입력 스트림으로 객체를 읽어오기
                if (response[0].equals("connectionTest")) { // 헤더가 connectionTest 인 경우
                    if (response[1].equals("pong")) {   // 응답이 퐁인 경우
                        ConnectionInfo.getInstance().setIn(in); // 입력 스트림 설정
                        ConnectionInfo.getInstance().setOut(out);   // 출력 스트림 설정
                        ConnectionInfo.getInstance().setSocket(socket); // 소켓 설정
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
