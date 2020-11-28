package chat;

import chat.client.frame.ConnectionSetting;
import chat.server.frame.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelector extends JFrame {
    // 모드 선택 프레임
    public ModeSelector() {
        setTitle("모드 선택");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(new JPanel(), BorderLayout.NORTH);
        container.add(new JPanel(), BorderLayout.SOUTH);
        container.add(new JPanel(), BorderLayout.EAST);
        container.add(new JPanel(), BorderLayout.WEST);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        container.add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // 설명 레이블
        JLabel label = new JLabel("채팅 프로그램 실행 모드 선택", JLabel.CENTER);
        gbc.weighty = 1.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        // 서버 선택 버튼
        JButton serverButton = new JButton("서버");

        // 서버 선택 버튼을 누를 때 Server 프레임을 실행하기 위해 ActionListener 추가
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Server();
            }
        });
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(serverButton, gbc);

        // 클라이언트 선택 버튼
        JButton clientButton = new JButton("클라이언트");

        // 클라이언트 선택 버튼을 누를 때 ConnectionSetting 프레임을 실행하기 위해 ActionListener 추가
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ConnectionSetting();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(clientButton, gbc);

        setLocationRelativeTo(null);
        setSize(250, 150);
        setVisible(true);
    }

}
