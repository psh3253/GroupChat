package chat.client.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {
    // 클라이언트 프레임
    public Client() {
        setTitle("채팅 프로그램");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JLabel label = new JLabel("로그인 및 회원가입 선택", JLabel.CENTER);
        gbc.weighty = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        // 로그인 선택 버튼
        JButton loginSelectButton = new JButton("로그인");

        // 로그인 선택 버튼을 누를 때 Login 프레임 실행하기 위해 ActionListener 추가
        loginSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Login();
            }
        });
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(loginSelectButton, gbc);

        // 회원가입 선택 버튼
        JButton registerSelectButton = new JButton("회원가입");

        // 회원가입 선택 버튼을 누를 때 Register 프레임을 실행하기 위해 ActionListener 추가
        registerSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Register();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(registerSelectButton, gbc);

        setLocationRelativeTo(null);
        setSize(200, 150);
        setVisible(true);
    }
}
