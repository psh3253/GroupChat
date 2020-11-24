package chat.client.listener;

import chat.client.network.ConnectionInfo;
import chat.client.network.ConnectionTermination;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class WindowCloseListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        ConnectionTermination.getInstance().disconnect();
    }
}
