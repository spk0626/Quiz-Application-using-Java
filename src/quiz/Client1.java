package quiz;

import java.io.*;
import java.net.*;
import javax.swing.*;
import quiz.application.Login;

public class Client1 implements Runnable {
	 private Socket socket;
	    private ObjectOutputStream outputStream;
	    private ObjectInputStream inputStream;

	    public Client1(Socket socket) {
	        this.socket = socket;
	    }

    public void run() {
        try {
            System.out.println("Connected to server.");

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject("Client1");

            String response = (String) inputStream.readObject();
            System.out.println("Server: " + response);
            
            SwingUtilities.invokeLater(() -> {
                Login login = new Login(outputStream, inputStream);
                login.setVisible(true);
            });


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12346);
            Client1 client1 = new Client1(socket);
            Thread t1 = new Thread(client1);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}