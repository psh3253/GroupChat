package chat.client.frame;

import chat.client.message.MessageReceiver;
import chat.client.message.MessageSender;
import chat.client.network.ConnectionTermination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatRoom extends JFrame {
    // 채팅방 프레임
    public ChatRoom() {
        setTitle("채팅 프로그램");

        // 프로그램을 종료할 때 서버와 연결을 종료하기 위해 WindowListener 추가
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConnectionTermination.getInstance().disconnect();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // 위 패널
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

        // 아래 패널
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        container.add(bottomPanel, BorderLayout.SOUTH);

        // 채팅 입력 텍스트 영역
        JTextField inputField = new JTextField(30);

        // 엔터 키를 누를 때 메시지가 전송되게 ActionListener 추가
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText(""); // 채팅 입력 텍스트 영역 초기화
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        bottomPanel.add(inputField, gbc);

        // 전송 버튼 패널
        JPanel sendButtonPanel = new JPanel();
        sendButtonPanel.setLayout(new BorderLayout());
        gbc.gridx = 1;
        gbc.gridy = 0;
        bottomPanel.add(sendButtonPanel, gbc);
        sendButtonPanel.add(new JPanel(), BorderLayout.NORTH);
        sendButtonPanel.add(new JPanel(), BorderLayout.SOUTH);
        sendButtonPanel.add(new JPanel(), BorderLayout.WEST);
        sendButtonPanel.add(new JPanel(), BorderLayout.EAST);

        // 전송 버튼
        JButton messageSendButton = new JButton("전송");

        // 전송 버튼을 누를 때 메시지가 전송되게 ActionListener 추가
        messageSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText(""); // 채팅 입력 텍스트 영역 초기화
            }
        });
        sendButtonPanel.add(messageSendButton, BorderLayout.CENTER);

        setLocation(500, 50);
        setSize(400, 600);
        setVisible(true);

        // 메시지를 수신하기 위해서 MessageReceiver Thread 생성 및 시작
        Thread thread = new Thread(new MessageReceiver(content, concurrentUserLabel));
        thread.start();
    }

    // 메시지 전송 함수
    public void sendMessage(String message) {
        MessageSender.getInstance().sendMessage(message);
    }
}
