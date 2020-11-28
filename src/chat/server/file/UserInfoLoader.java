package chat.server.file;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UserInfoLoader {
    private final HashMap<String, String> userList = new HashMap<String, String>(); // 유저 목록

    private static UserInfoLoader instance = null;  // 인스턴스

    // 인스턴스화된 UserInfoLoader 객체 반환 함수
    public static UserInfoLoader getInstance() {
        if (instance == null)
            instance = new UserInfoLoader();
        return instance;
    }

    // 유저 데이터 파일 불러오기 함수
    public void readUserInfoFile() {
        File file = new File("data.txt");
        if (!file.exists()) {   // 파일이 존재하지 않는 경우
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

            // 파일의 내용을 모두 입력 받아 유저 목록에 저장
            while (scanner.hasNext()) {
                String id = scanner.next();
                String passwd = scanner.next();
                userList.put(id, passwd);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "데이터 파일을 불러오는데 실패하였습니다.", "파일 로딩 실패", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    // 유저 데이터 파일 저장 함수
    public void writeUserInfoFile() {
        try {
            FileWriter fileWriter = new FileWriter("data.txt");

            // 유저 목록을 파일의 내용으로 저장
            userList.forEach((id, passwd) -> {
                try {
                    fileWriter.write(id + " " + passwd + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 유저 목록 가져오기 함수
    public HashMap<String, String> getUserList() {
        return userList;
    }

    // 유저 추가 함수
    public void addUserList(String id, String passwd) {
        userList.put(id, passwd);
    }
}
