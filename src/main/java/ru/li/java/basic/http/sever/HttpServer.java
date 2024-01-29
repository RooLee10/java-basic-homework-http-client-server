package ru.li.java.basic.http.sever;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.li.java.basic.http.sever.handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {
    private final int port;
    private final Dispatcher dispatcher;
    private final Logger logger;

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.logger = LogManager.getLogger(HttpServer.class.getName());
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("Подключился клиент");
                try {
                    new ClientHandler(this, socket);
                } catch (IOException e) {
                    logger.error("Не удалось подключить клиента\n" + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("Не удалось запустить сервер\n" + e.getMessage());
        }
    }
}
