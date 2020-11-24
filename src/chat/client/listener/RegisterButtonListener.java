package chat.client.listener;

import chat.client.frame.ChatRoom;
import chat.client.network.RegisterManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterButtonListener implements ActionListener {
    private final String id;
    private final String passwd;

    public RegisterButtonListener(JTextField idField, JPasswordField passwdField) {
        this.id = idField.getText();
        this.passwd = new String(passwdField.getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton loginButton = (JButton) e.getSource();
        RegisterManager.getInstance().Register(id, passwd, loginButton);
    }
}
