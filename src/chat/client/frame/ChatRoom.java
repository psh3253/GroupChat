package chat.client.frame;

import chat.client.listener.WindowCloseListener;
import chat.client.message.MessageReceiver;
import chat.client.message.MessageSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatRoom extends JFrame {
    public ChatRoom() {
        setTitle("채팅 프로그램");
        addWindowListener(new WindowCloseListener());
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

        JTextField inputField = new JTextField(30);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        bottomPanel.add(inputField, gbc);

        JPanel sendButtonPanel = new JPanel();
        sendButtonPanel.setLayout(new BorderLayout());
        gbc.gridx = 1;
        gbc.gridy = 0;
        bottomPanel.add(sendButtonPanel, gbc);
        sendButtonPanel.add(new JPanel(), BorderLayout.NORTH);
        sendButtonPanel.add(new JPanel(), BorderLayout.SOUTH);
        sendButtonPanel.add(new JPanel(), BorderLayout.WEST);
        sendButtonPanel.add(new JPanel(), BorderLayout.EAST);

        JButton messageSendButton = new JButton("전송");
        messageSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });
        sendButtonPanel.add(messageSendButton, BorderLayout.CENTER);

        setLocation(500, 50);
        setSize(400, 600);
        setVisible(true);

        Thread thread = new Thread(new MessageReceiver(content, concurrentUserLabel));
        thread.start();
    }

    public void sendMessage(String message) {
        MessageSender.getInstance().sendMessage(message);
    }
}
