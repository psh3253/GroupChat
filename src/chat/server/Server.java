package chat.server;

import chat.server.network.ServerRunnable;

import javax.swing.*;
import java.awt.*;

public class Server extends JFrame {
    public Server() {
        setTitle("채팅 서버 프로그램");
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

        JLabel titleLabel = new JLabel("단체 채팅방");
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(titleLabel, gbc);

        JLabel concurrentUserLabel = new JLabel("동시 접속자수 : 0명");
        gbc.gridx = 1;
        gbc.gridy = 0;
        topPanel.add(concurrentUserLabel, gbc);

        JButton concurrentUserListButton = new JButton("접속자 목록");
        gbc.gridx = 2;
        gbc.gridy = 0;
        topPanel.add(concurrentUserListButton, gbc);

        JTextArea content = new JTextArea();
        content.setLineWrap(true);
        content.setEditable(false);
        JScrollPane contentScroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        container.add(contentScroll, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        container.add(scrollPane, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        container.add(bottomPanel, BorderLayout.SOUTH);

        setLocation(500, 50);
        setSize(400, 600);
        setVisible(true);

        Thread thread = new Thread(new ServerRunnable(content, concurrentUserLabel));
        thread.start();
    }
}
