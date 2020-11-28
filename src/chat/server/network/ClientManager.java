package chat.server.network;

import chat.server.file.UserInfoLoader;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientManager implements Runnable {
    public static final int LOGIN_SUCCESS = 0;

    public static final int ID_NOT_FOUND = 1;

    public static final int PASSWORD_INCORRECT = 2;

    public static final int REGISTER_SUCCESS = 3;

    public static final int ID_ALREADY_EXIT = 4;

    private static final ArrayList<ClientList> clientLists = new ArrayList<>(); // 접속중인 클라이언트 목록

    private final Socket socket;    // 소켓

    private final JTextArea content;    // 채팅이 표시되는 텍스트 영역

    private final JLabel concurrentUserLabel;   // 동시 접속자수 표시 레이블

    ClientManager(Socket socket, JTextArea content, JLabel concurrentUserLabel) {
        this.socket = socket;
        this.content = content;
        this.concurrentUserLabel = concurrentUserLabel;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        ObjectOutputStream out;
        String[] readObject;
        ClientList clientList1 = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream()); // 출력 스트림 생성
            in = new ObjectInputStream(socket.getInputStream());    // 입력 스트림 생성
            while (true) {
                try {
                    readObject = (String[]) in.readObject();    // 입력 스트림으로 객체를 읽어오기
                    // 헤더가 connectionTest 이고 내용이 핑인 경우
                    if (readObject[0].equals("connectionTest") && readObject[1].equals("ping")) {
                        String[] pongObject;
                        pongObject = new String[2];
                        pongObject[0] = "connectionTest";   // connectionTest 헤더
                        pongObject[1] = "pong"; // 퐁
                        out.writeObject(pongObject);    // 출력 스트림에 pongObject 내보내기
                        out.flush();    // 전송
                    } else if (readObject[0].equals("disconnect")) {    // 헤더가 disconnect 인 경우
                        clientLists.remove(clientList1);    // 접속중인 클라이언트 목록에서 현재 클라이언트 제거
                        socket.close(); // 소켓 닫기
                        String[] quitObject = new String[2];
                        quitObject[0] = "quit"; // quit 헤더
                        quitObject[1] = readObject[1];  // 종료된 클라이언트의 유저 아이디

                        // 접속중인 모든 클라이언트에게 유저가 퇴장했다는 메시지 전송
                        for (ClientList clientList : clientLists) {
                            ObjectOutputStream out1 = clientList.getOut();
                            out1.writeObject(quitObject);
                            out1.flush();
                        }
                        UpdateConcurrentUser(); // 접속중인 모든 클라이언트에게 동시 접속자수 업데이트
                        content.append(readObject[1] + "님이 퇴장하셨습니다.\n");
                        concurrentUserLabel.setText("동시 접속자수 : " + clientLists.size() + "명");
                        break;  // 반복문을 빠져나와 쓰레드 종료
                    } else if (readObject[0].equals("login")) { // 헤더가 login 인 경우
                        int loginResponseCode = Login(readObject[1], readObject[2]);    // 로그인
                        String[] loginResponseObject;
                        loginResponseObject = new String[2];
                        loginResponseObject[0] = "loginResponse";   // loginResponse 헤더
                        loginResponseObject[1] = Integer.toString(loginResponseCode);   // 응답 코드
                        out.writeObject(loginResponseObject);   // 출력 스트림에 loginResponseObject 내보내기
                        out.flush();    // 전송
                        if (loginResponseCode == LOGIN_SUCCESS) {
                            clientList1 = new ClientList(readObject[1], socket, in, out);   // 획득한 정보를 기반으로 클라이언트 정보 작성
                            clientLists.add(clientList1);   // 접속중인 클라이언트 목록에 현재 클라이언트 추가
                            String[] joinObject = new String[2];
                            joinObject[0] = "join"; // join 헤더
                            joinObject[1] = readObject[1];  // 입장한 클라이언트의 유저 아이디

                            // 접속중인 모든 클라이언트에게 유저가 입장했다는 메시지 전송
                            for (ClientList clientList : clientLists) {
                                ObjectOutputStream out1 = clientList.getOut();
                                out1.writeObject(joinObject);
                                out1.flush();
                            }
                            UpdateConcurrentUser(); // 접속중인 모든 클라이언트에게 동시 접속자수 업데이트
                            content.append(readObject[1] + "님이 입장하셨습니다.\n");
                            concurrentUserLabel.setText("동시 접속자수 : " + clientLists.size() + "명");
                        }
                    } else if (readObject[0].equals("register")) {  // 헤더가 register 인 경우
                        int registerResponseCode = Register(readObject[1], readObject[2]);  // 회원가입
                        String[] registerResponseObject;
                        registerResponseObject = new String[2];
                        registerResponseObject[0] = "registerResponse"; // registerResponse 헤더
                        registerResponseObject[1] = Integer.toString(registerResponseCode); // 응답 코드
                        out.writeObject(registerResponseObject);    // 출력 스트림에 registerObject 내보내기
                        out.flush();    // 전송
                    } else if (readObject[0].equals("message")) {   // 헤더가 message 인 경우
                        String[] messageObject;
                        messageObject = new String[3];
                        messageObject[0] = "message";   // message 헤더
                        messageObject[1] = readObject[1];   // 유저 아이디
                        messageObject[2] = readObject[2];   // 메시지 내용
                        content.append(readObject[1] + "  >> " + readObject[2] + "\n");
                        // 접속중인 모든 클라이언트에게 메시지 전송
                        for (ClientList clientList : clientLists) {
                            ObjectOutputStream out1 = clientList.getOut();
                            out1.writeObject(messageObject);
                            out1.flush();
                        }
                    }
                } catch (SocketException e) {
                    //e.printStackTrace();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 동시 접속자수 업데이트 함수
    public void UpdateConcurrentUser() {
        String[] concurrentUserCount = new String[2];
        concurrentUserCount[0] = "concurrentUserCount"; // concurrentUserCount 헤더
        concurrentUserCount[1] = String.valueOf(clientLists.size());    // 접속중인 클라이언트수
        // 접속중인 모든 클라이언트에게 변경된 동시 접속자수 전송
        for (ClientList clientList : clientLists) {
            ObjectOutputStream out1 = clientList.getOut();
            try {
                out1.writeObject(concurrentUserCount);
                out1.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 로그인 함수
    public int Login(String id, String passwd) {
        HashMap<String, String> userList = UserInfoLoader.getInstance().getUserList();  // 유저 목록 가져오기
        if (!userList.containsKey(id)) {    // 존재하지 않는 아이디인 경우
            return ID_NOT_FOUND;
        } else if (!userList.get(id).equals(passwd)) {  // 비밀번호가 일치하지 않는 경우
            return PASSWORD_INCORRECT;
        } else {
            return LOGIN_SUCCESS;
        }
    }

    // 회원가입 함수
    public int Register(String id, String passwd) {
        HashMap<String, String> userList = UserInfoLoader.getInstance().getUserList();  // 유저 목록 가져오기
        if (userList.containsKey(id)) { // 아이디가 이미 존재하는 경우
            return ID_ALREADY_EXIT;
        } else {
            UserInfoLoader.getInstance().addUserList(id, passwd);   // 유저 목록 추가
            return REGISTER_SUCCESS;
        }
    }
}
