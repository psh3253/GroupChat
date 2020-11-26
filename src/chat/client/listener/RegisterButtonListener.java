package chat.client.listener;

import chat.client.frame.Login;
import chat.client.network.RegisterManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterButtonListener implements ActionListener {
    private final JTextField idField;
    private final JPasswordField passwdField;

    public RegisterButtonListener(JTextField idField, JPasswordField passwdField) {
        this.idField = idField;
        this.passwdField = passwdField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton registerButton = (JButton) e.getSource();

        String id = idField.getText();
        String passwd = new String(passwdField.getPassword());
        boolean isSuccess = RegisterManager.getInstance().Register(id, passwd, registerButton);
        if (isSuccess) {
            registerButton.getTopLevelAncestor().setVisible(false);
            new Login();
        }
    }
}
