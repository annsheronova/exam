package server;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Slf4j
public class Server {

    public final static Integer SERVER_PORT = 5000;
    static Map<Integer, ClientHandler> clients = new HashMap<>();
    static ReadWriteLock rwlock = new ReentrantReadWriteLock();
    final FileWriter writer = new FileWriter("res.txt");


    public Server() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            log.info("Сервер запущен, ждет подключения клиентов");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                log.info("Подключился клиент с порта {}", clientSocket.getPort());
                ClientHandler clientHandler = new ClientHandler(clientSocket, writer);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            writer.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }


}
