package chat.client.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionInfo {

    private static ConnectionInfo instance = null;  // 인스턴스

    private String address = "localhost";   // 서버 주소(기본값 : localhost)

    private Socket socket = null;   // 소켓

    private String id;  // 아이디

    private ObjectInputStream in;   // 입력 스트림

    private ObjectOutputStream out; // 출력 스트림

    // 인스턴스화된 ConnectionInfo 객체 반환 함수
    public static ConnectionInfo getInstance() {
        if (instance == null)
            instance = new ConnectionInfo();
        return instance;
    }

    // 서버 주소 가져오기 함수
    public String getAddress() {
        return address;
    }

    // 서버 주소 설정 함수
    public void setAddress(String address) {
        this.address = address;
    }

    // 서버 포트 가져오기 함수
    public int getPort() {
        return 9999;
    }

    // 소켓 가져오기 함수
    public Socket getSocket() {
        return socket;
    }

    // 소켓 설정 함수
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    // 아이디 가져오기 함수
    public String getId() {
        return id;
    }

    // 아이디 설정 함수
    public void setId(String id) {
        this.id = id;
    }

    // 입력 스트림 설정 함수
    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    // 출력 스트림 설정 함수
    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    // 입력 스트림 가져오기 함수
    public ObjectInputStream getIn() {
        return in;
    }

    // 출력 스트림 가져오기 함수
    public ObjectOutputStream getOut() {
        return out;
    }
}
