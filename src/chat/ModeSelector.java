package chat;

import chat.client.frame.ConnectionSetting;
import chat.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelector extends JFrame {
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

        JLabel label = new JLabel("채팅 프로그램 실행 모드 선택", JLabel.CENTER);
        gbc.weighty = 1.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        JButton serverButton = new JButton("서버");
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

        JButton clientButton = new JButton("클라이언트");
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
