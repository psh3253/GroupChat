package chat.server.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientList {

    private final String id;    // 클라이언트 아이드

    private final Socket socket;    // 클라이언트 소켓

    private final ObjectInputStream in; // 클라이언트 입력 스트림

    private final ObjectOutputStream out;   // 클라이언트 출력 스트림

    public ClientList(String id, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.id = id;
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    // 클라이언트 아이디 가져오기 함수
    public String getId() {
        return id;
    }

    // 클라이언트 소켓 가져오기 함수
    public Socket getSocket() {
        return socket;
    }

    // 클라이언트 입력 스트림 가져오기 함수
    public ObjectInputStream getIn() {
        return in;
    }

    // 클라이언트 출력 스트림 가져오기 함수
    public ObjectOutputStream getOut() {
        return out;
    }

}
