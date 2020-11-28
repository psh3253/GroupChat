package chat.server.frame;

import chat.server.file.UserInfoLoader;
import chat.server.network.ServerRunnable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Server extends JFrame {
    // 서버 프레임
    public Server() {
        setTitle("채팅 서버 프로그램");

        // 프로그램을 종료할 때 유저 목록을 파일로 저장하기 위해 WindowListener 추가
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserInfoLoader.getInstance().writeUserInfoFile();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        container.add(northPanel, BorderLayout.NORTH);
        northPanel.add(new JPanel(), BorderLayout.NORTH);
        northPanel.add(new JPanel(), BorderLayout.SOUTH);
        northPanel.add(new JPanel(), BorderLayout.EAST);
        northPanel.add(new JPanel(), BorderLayout.WEST);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        northPanel.add(topPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // 설명 레이블
        JLabel titleLabel = new JLabel("단체 채팅방");
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(titleLabel, gbc);

        // 동시 접속자수 표시 레이블
        JLabel concurrentUserLabel = new JLabel("동시 접속자수 : 0명");
        gbc.gridx = 1;
        gbc.gridy = 0;
        topPanel.add(concurrentUserLabel, gbc);

        // 채팅이 표시되는 텍스트 영역
        JTextArea content = new JTextArea();
        content.setLineWrap(true);
        content.setEditable(false);

        // 위 텍스트 영역에 수직 스크롤바 추가
        JScrollPane contentScroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        container.add(contentScroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        container.add(bottomPanel, BorderLayout.SOUTH);

        setLocation(500, 50);
        setSize(400, 600);
        setVisible(true);

        // 클라이언트의 연결을 수락하기 위해서 ServerRunnable Thread 생성 및 시작
        Thread thread = new Thread(new ServerRunnable(content, concurrentUserLabel));
        thread.start();

        // 유저 목록 파일 불러오기
        UserInfoLoader.getInstance().readUserInfoFile();
    }
}
