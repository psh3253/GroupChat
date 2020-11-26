package chat.client.listener;

import chat.client.frame.Client;
import chat.client.network.ConnectionInfo;
import chat.client.network.ConnectionTest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionSettingButtonListener implements ActionListener {
    private final String address;

    public ConnectionSettingButtonListener(JTextField addressField) {
        this.address = addressField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton connectionSettingButton = (JButton) e.getSource();
        ConnectionInfo.getInstance().setAddress(address);
        if (ConnectionTest.getInstance().PingPong()) {
            connectionSettingButton.getTopLevelAncestor().setVisible(false);
            new Client();
        } else {
            JOptionPane.showMessageDialog(connectionSettingButton, "서버 접속에 실패하였습니다.", "접속 실패", JOptionPane.WARNING_MESSAGE);
        }
    }
}
