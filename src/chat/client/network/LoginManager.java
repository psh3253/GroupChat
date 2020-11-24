package chat.client.network;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class LoginManager {
    public static final int LOGIN_SUCCESS = 0;

    public static final int ID_NOT_FOUND = 1;

    public static final int PASSWORD_INCORRECT = 2;

    private static LoginManager instance = null;

    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    public boolean Login(String id, String passwd, JButton loginButton) {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        String[] loginObject = new String[3];
        loginObject[0] = "login";
        loginObject[1] = id;
        loginObject[2] = passwd;
        System.out.println(Arrays.toString(loginObject));
        try {
            socket = ConnectionInfo.getInstance().getSocket();
            in = ConnectionInfo.getInstance().getIn();
            out = ConnectionInfo.getInstance().getOut();
            out.writeObject(loginObject);
            out.flush();
            while (true) {
                String[] response = (String[]) in.readObject();
                if (response[0].equals("loginResponse")) {
                    int responseCode = Integer.parseInt(response[1]);
                    if (responseCode == LOGIN_SUCCESS) {
                        ConnectionInfo.getInstance().setId(id);
                        return true;
                    } else if (responseCode == ID_NOT_FOUND) {
                        JOptionPane.showMessageDialog(loginButton, "아이디가 존재하지 않습니다.", "로그인 실패", JOptionPane.WARNING_MESSAGE);
                        return false;
                    } else if (responseCode == PASSWORD_INCORRECT) {
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
