package chat.client.network;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginManager {
    public static final int LOGIN_SUCCESS = 0;

    public static final int ID_NOT_FOUND = 1;

    public static final int PASSWORD_INCORRECT = 2;

    private static LoginManager instance = null;    // 인스턴스

    // 인스턴스화된 LoginManager 객체 반환 함수
    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    // 로그인 함수
    public boolean Login(String id, String passwd, JButton loginButton) {
        ObjectInputStream in;
        ObjectOutputStream out;

        String[] loginObject = new String[3];
        loginObject[0] = "login";   // login 헤더
        loginObject[1] = id;    // 아이디
        loginObject[2] = passwd;    // 비밀번호
        try {
            in = ConnectionInfo.getInstance().getIn();  // 입력 스트림 가져오기
            out = ConnectionInfo.getInstance().getOut();    // 출력 스트림 가져오기
            out.writeObject(loginObject);   // 출력 스트림에 loginObject 내보내기
            out.flush();    // 전송
            while (true) {
                String[] response = (String[]) in.readObject(); // 입력 스트림으로 객체를 읽어오기
                if (response[0].equals("loginResponse")) {  // 헤더가 loginResponse 인 경우
                    int responseCode = Integer.parseInt(response[1]);   // 응답 코드
                    if (responseCode == LOGIN_SUCCESS) {    // 로그인을 성공한 경우
                        ConnectionInfo.getInstance().setId(id); // 아아디 설정
                        return true;
                    } else if (responseCode == ID_NOT_FOUND) {  // 존재하지 않는 아이디인 경우
                        JOptionPane.showMessageDialog(loginButton, "아이디가 존재하지 않습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
                        return false;
                    } else if (responseCode == PASSWORD_INCORRECT) {    // 비밀번호가 일치하지 않는 경우
                        JOptionPane.showMessageDialog(loginButton, "비밀번호가 일치하지 않습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(loginButton, "로그인중에 문제가 발생하였습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
