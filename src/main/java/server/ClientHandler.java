package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

import static client.Client.QUIT_MESSAGE;


public class ClientHandler extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private Socket clientSocket;
    private BufferedReader reader;
    private FileWriter writer;

    public ClientHandler(Socket clientSocket, FileWriter writer) {
        this.writer = writer;
        this.clientSocket = clientSocket;
        Server.clients.put(clientSocket.getPort(), this);
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.equals(QUIT_MESSAGE)) {
                    Server.clients.remove(clientSocket.getPort());
                    break;
                }
                writeToFile(message);

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String message) throws IOException, InterruptedException {
        synchronized (writer) {
            Thread.sleep(3000);
            writer.write(clientSocket.getPort() + ": " + message + "\n");
            writer.flush();
        }
    }


}
