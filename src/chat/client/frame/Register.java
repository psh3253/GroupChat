package chat.client.frame;

import chat.client.listener.RegisterButtonListener;
import chat.client.network.ConnectionTermination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Register extends JFrame {
    // 회원가입 프레임
    public Register() {
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

        // 아이디 레이블
        JLabel idLabel = new JLabel("아이디");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        // 비밀번호 레이블
        JLabel passwdLabel = new JLabel("비밀번호");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwdLabel, gbc);

        // 아이디 입력 텍스트 필드
        JTextField idField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(idField, gbc);

        // 비밀번호 입력 텍스트 필드
        JPasswordField passwdField = new JPasswordField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwdField, gbc);

        // 회원가입 버튼
        JButton registerButton = new JButton("회원가입");

        // 회원가입 버튼을 누를 때 회원가입 하기 위해 RegisterButtonLister 추가
        registerButton.addActionListener(new RegisterButtonListener(idField, passwdField));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        setLocationRelativeTo(null);
        setSize(250, 150);
        setVisible(true);
    }
}
