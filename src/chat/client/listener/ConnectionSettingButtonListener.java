package chat.client.listener;

import chat.client.frame.Client;
import chat.client.network.ConnectionInfo;
import chat.client.network.ConnectionTest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionSettingButtonListener implements ActionListener {
    private final String address;
    private final int port;

    public ConnectionSettingButtonListener(JTextField addressField, JTextField portField) {
        this.address = addressField.getText();
        this.port = Integer.parseInt(portField.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton connectionSettingButton = (JButton) e.getSource();
        ConnectionInfo.getInstance().setAddress(address);
        ConnectionInfo.getInstance().setPort(port);
        if (ConnectionTest.getInstance().PingPong()) {
            connectionSettingButton.getTopLevelAncestor().setVisible(false);
            new Client();
        } else {
            JOptionPane.showMessageDialog(connectionSettingButton, "서버 접속에 실패하였습니다.", "접속 실패", JOptionPane.WARNING_MESSAGE);
        }
    }
}
