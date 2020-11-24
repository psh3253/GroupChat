package chat.server.network;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientManager implements Runnable {
    public static final int LOGIN_SUCCESS = 0;

    public static final int ID_NOT_FOUND = 1;

    public static final int PASSWORD_INCORRECT = 2;

    private static final ArrayList<ClientList> clientLists = new ArrayList<>();

    private final Socket socket;

    private final JTextArea content;

    private final JLabel concurrentUserLabel;

    ClientManager(Socket socket, JTextArea content, JLabel concurrentUserLabel) {
        this.socket = socket;
        this.content = content;
        this.concurrentUserLabel = concurrentUserLabel;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        ObjectOutputStream out;
        String[] readObject;
        String[] writeObject;
        ClientList clientList1 = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    readObject = (String[]) in.readObject();
                    System.out.println(Arrays.toString(readObject));
                    if (readObject[0].equals("connectionTest") && readObject[1].equals("ping")) {
                        writeObject = new String[2];
                        writeObject[0] = "connectionTest";
                        writeObject[1] = "pong";
                        out.writeObject(writeObject);
                        out.flush();
                    } else if (readObject[0].equals("disconnect")) {
                        clientLists.remove(clientList1);
                        socket.close();
                        UpdateConcurrentUser();
                        concurrentUserLabel.setText("동시 접속자수 : " + clientLists.size() + "명");
                        break;
                    } else if (readObject[0].equals("login")) {
                        //로그인 성공하면
                        writeObject = new String[2];
                        writeObject[0] = "loginResponse";
                        writeObject[1] = Integer.toString(LOGIN_SUCCESS);
                        clientList1 = new ClientList(readObject[1], socket, in, out);
                        clientLists.add(clientList1);
                        out.writeObject(writeObject);
                        out.flush();
                        for (ClientList clientList : clientLists) {
                            String[] joinObject = new String[2];
                            joinObject[0] = "join";
                            joinObject[1] = readObject[1];
                            ObjectOutputStream out1 = clientList.getOut();
                            out1.writeObject(joinObject);
                            out1.flush();
                        }
                        UpdateConcurrentUser();
                        concurrentUserLabel.setText("동시 접속자수 : " + clientLists.size() + "명");
                    } else if (readObject[0].equals("register")) {

                    } else if (readObject[0].equals("message")) {
                        writeObject = new String[3];
                        writeObject[0] = "message";
                        writeObject[1] = readObject[1];
                        writeObject[2] = readObject[2];
                        content.append(readObject[1] + "  >> " + readObject[2] + "\n");
                        for (ClientList clientList : clientLists) {
                            ObjectOutputStream out1 = clientList.getOut();
                            out1.writeObject(writeObject);
                            out1.flush();
                        }
                    }
                } catch (SocketException e) {
                    //e.printStackTrace();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdateConcurrentUser() throws IOException {
        String[] concurrentUserCount = new String[2];
        concurrentUserCount[0] = "concurrentUserCount";
        concurrentUserCount[1] = String.valueOf(clientLists.size());
        for (ClientList clientList : clientLists) {
            ObjectOutputStream out1 = clientList.getOut();
            out1.writeObject(concurrentUserCount);
            out1.flush();
        }
    }
}
