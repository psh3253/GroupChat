package chat.client.network;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegisterManager {
    public static final int REGISTER_SUCCESS = 3;

    public static final int ID_ALREADY_EXIT = 4;

    private static RegisterManager instance = null; // 인스턴스

    // 인스턴스화된 RegisterManager 객체 반환 함수
    public static RegisterManager getInstance() {
        if (instance == null)
            instance = new RegisterManager();
        return instance;
    }

    // 회원가입 함수
    public boolean Register(String id, String passwd, JButton registerButton) {
        ObjectInputStream in;
        ObjectOutputStream out;

        String[] registerObject = new String[3];
        registerObject[0] = "register"; // register 헤더
        registerObject[1] = id; // 아이디
        registerObject[2] = passwd; // 비밀번호
        try {
            in = ConnectionInfo.getInstance().getIn();  // 입력 스트림 가져오기
            out = ConnectionInfo.getInstance().getOut();    // 출력 스트림 가져오기
            out.writeObject(registerObject);    // 출력 스트림에 registerObject 내보내기
            out.flush();    // 전송
            while (true) {
                String[] response = (String[]) in.readObject(); // 입력 스트림으로 객체를 읽어오기
                if (response[0].equals("registerResponse")) {   // 헤더가 registerResponse 인 경우
                    int responseCode = Integer.parseInt(response[1]);   // 응답 코드
                    if (responseCode == REGISTER_SUCCESS) { // 회원가입에 성공한 경우
                        JOptionPane.showMessageDialog(registerButton, "회원가입이 완료되었습니다.");
                        return true;
                    } else if (responseCode == ID_ALREADY_EXIT) {   // 이미 존재하는 아이디인 경우
                        JOptionPane.showMessageDialog(registerButton, "이미 존재하는 아이디 입니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(registerButton, "회원가입중에 문제가 발생하였습니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
