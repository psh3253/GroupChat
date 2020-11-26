package chat.server.file;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class UserInfoLoader {
    private HashMap<String, String> userList;

    private static UserInfoLoader instance = null;

    public static UserInfoLoader getInstance() {
        if (instance == null)
            instance = new UserInfoLoader();
        return instance;
    }

    public void readUserInfoFile() {
        File file = new File("data.dat");
        if (!file.exists()) {
            try {
                FileWriter fileWriter = new FileWriter("data.txt");
                fileWriter.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "데이터 파일을 생성하는데 실패하였습니다.", "파일 생성 실패", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
        }
        try {
            File file1 = new File("data.txt");
            Scanner scanner = new Scanner(file1);
            while (scanner.hasNext())
            {
                String id = scanner.next();
                String passwd = scanner.next();
                userList.put(id, passwd);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "데이터 파일을 불러오는데 실패하였습니다.", "파일 로딩 실패", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public void writeUserInfoFile() {
        try {
            FileWriter fileWriter = new FileWriter("data.txt");
            // 파일 저장해라
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getUserList() {
        return userList;
    }

    public void addUserList(String id, String passwd) {
        userList.put(id, passwd);
    }
}
