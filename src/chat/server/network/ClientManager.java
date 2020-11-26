package chat.server.network;

import chat.server.file.UserInfoLoader;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClientManager implements Runnable {
    public static final int LOGIN_SUCCESS = 0;

    public static final int ID_NOT_FOUND = 1;

    public static final int PASSWORD_INCORRECT = 2;

    public static final int REGISTER_SUCCESS = 3;

    public static final int ID_ALREADY_EXIT = 4;

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
        ClientList clientList1 = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    readObject = (String[]) in.readObject();
                    System.out.println(Arrays.toString(readObject));
                    if (readObject[0].equals("connectionTest") && readObject[1].equals("ping")) {
                        String[] pongObject;
                        pongObject = new String[2];
                        pongObject[0] = "connectionTest";
                        pongObject[1] = "pong";
                        out.writeObject(pongObject);
                        out.flush();
                    } else if (readObject[0].equals("disconnect")) {
                        clientLists.remove(clientList1);
                        socket.close();
                        String[] quitObject = new String[2];
                        quitObject[0] = "quit";
                        quitObject[1] = readObject[1];
                        for (ClientList clientList : clientLists) {
                            ObjectOutputStream out1 = clientList.getOut();
                            out1.writeObject(quitObject);
                            out1.flush();
                        }
                        UpdateConcurrentUser();
                        content.append(readObject[1] + "님이 퇴장하셨습니다.\n");
                        concurrentUserLabel.setText("동시 접속자수 : " + clientLists.size() + "명");
                        break;
                    } else if (readObject[0].equals("login")) {
                        int loginResponseCode = Login(readObject[1], readObject[2]);
                        String[] loginResponseObject;
                        loginResponseObject = new String[2];
                        loginResponseObject[0] = "loginResponse";
                        loginResponseObject[1] = Integer.toString(loginResponseCode);
                        out.writeObject(loginResponseObject);
                        out.flush();
                        if (loginResponseCode == LOGIN_SUCCESS) {
                            clientList1 = new ClientList(readObject[1], socket, in, out);
                            clientLists.add(clientList1);
                            String[] joinObject = new String[2];
                            joinObject[0] = "join";
                            joinObject[1] = readObject[1];
                            for (ClientList clientList : clientLists) {
                                ObjectOutputStream out1 = clientList.getOut();
                                out1.writeObject(joinObject);
                                out1.flush();
                            }
                            UpdateConcurrentUser();
                            content.append(readObject[1] + "님이 입장하셨습니다.\n");
                            concurrentUserLabel.setText("동시 접속자수 : " + clientLists.size() + "명");
                        }
                    } else if (readObject[0].equals("register")) {
                        int registerResponseCode = Register(readObject[1], readObject[2]);
                        String[] registerResponseObject;
                        registerResponseObject = new String[2];
                        registerResponseObject[0] = "registerResponse";
                        registerResponseObject[1] = Integer.toString(registerResponseCode);
                        out.writeObject(registerResponseObject);
                        out.flush();
                    } else if (readObject[0].equals("message")) {
                        String[] messageObject;
                        messageObject = new String[3];
                        messageObject[0] = "message";
                        messageObject[1] = readObject[1];
                        messageObject[2] = readObject[2];
                        content.append(readObject[1] + "  >> " + readObject[2] + "\n");
                        for (ClientList clientList : clientLists) {
                            ObjectOutputStream out1 = clientList.getOut();
                            out1.writeObject(messageObject);
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

    public int Login(String id, String passwd) {
        HashMap<String, String> userList = UserInfoLoader.getInstance().getUserList();
        if (!userList.containsKey(id)) {
            return ID_NOT_FOUND;
        } else if (!userList.get(id).equals(passwd)) {
            return PASSWORD_INCORRECT;
        } else {
            return LOGIN_SUCCESS;
        }
    }

    public int Register(String id, String passwd) {
        HashMap<String, String> userList = UserInfoLoader.getInstance().getUserList();
        if (userList.containsKey(id)) {
            return ID_ALREADY_EXIT;
        } else {
            UserInfoLoader.getInstance().addUserList(id, passwd);
            return REGISTER_SUCCESS;
        }
    }
}
