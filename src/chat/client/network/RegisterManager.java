package chat.client.network;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class RegisterManager {
    public static final int REGISTER_SUCCESS = 0;

    public static final int ID_ALREADY_EXIT = 1;

    private static RegisterManager instance = null;

    public static RegisterManager getInstance() {
        if (instance == null)
            instance = new RegisterManager();
        return instance;
    }

    public void Register(String id, String passwd, JButton registerButton) {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        String[] RegisterObject = new String[3];
        RegisterObject[0] = "register";
        RegisterObject[1] = id;
        RegisterObject[2] = passwd;

        try {
            socket = new Socket(ConnectionInfo.getInstance().getAddress(), ConnectionInfo.getInstance().getPort());
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(RegisterObject);
            out.flush();
            while (true) {
                String[] response = (String[]) in.readObject();
                if (response[0].equals("registerResponse")) {
                    int responseCode = Integer.parseInt(response[1]);
                    if (responseCode == REGISTER_SUCCESS) {
                        JOptionPane.showMessageDialog(registerButton, "회원가입이 완료되었습니다.");
                        return;
                    } else if (responseCode == ID_ALREADY_EXIT) {
                        JOptionPane.showMessageDialog(registerButton, "이미 존재하는 아이디 입니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(registerButton, "회원가입중에 문제가 발생하였습니다.", "회원가입 실패", JOptionPane.WARNING_MESSAGE);
        }
    }
}
