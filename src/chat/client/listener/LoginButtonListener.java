package chat.client.listener;

import chat.client.frame.ChatRoom;
import chat.client.network.LoginManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginButtonListener implements ActionListener {
    private final JTextField idField;
    private final JPasswordField passwdField;

    public LoginButtonListener(JTextField idField, JPasswordField passwdField) {
        this.idField = idField;
        this.passwdField = passwdField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton loginButton = (JButton) e.getSource();
        String id = idField.getText();
        String passwd = new String(passwdField.getPassword());
        boolean isSuccess = LoginManager.getInstance().Login(id, passwd, loginButton);
        if (isSuccess) {
            loginButton.getTopLevelAncestor().setVisible(false);
            new ChatRoom();
        }
    }
}
