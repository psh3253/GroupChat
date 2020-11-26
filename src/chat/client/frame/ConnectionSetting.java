package chat.client.frame;

import chat.client.listener.ConnectionSettingButtonListener;

import javax.swing.*;
import java.awt.*;

public class ConnectionSetting extends JFrame {
    public ConnectionSetting() {
        setTitle("연결 설정");
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

        JLabel addressLabel = new JLabel("서버 주소");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addressLabel, gbc);

        JTextField addressField = new JTextField(10);
        addressField.setText("localhost");
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(addressField, gbc);

        JButton ConnectionSettingButton = new JButton("확인");
        ConnectionSettingButton.addActionListener(new ConnectionSettingButtonListener(addressField));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(ConnectionSettingButton, gbc);

        setLocationRelativeTo(null);
        setSize(230, 120);
        setVisible(true);
    }
}
