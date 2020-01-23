package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

import static client.Client.QUIT_MESSAGE;


public class Sender extends Thread {

    private BufferedWriter writer;

    Sender(BufferedWriter writer) {
        this.writer = writer;
        start();
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            String message;
            while (!(message = scanner.nextLine()).isEmpty()) {
                writer.write(message);
                writer.newLine();
                writer.flush();
                if (message.equals(QUIT_MESSAGE)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
