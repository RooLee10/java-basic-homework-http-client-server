package ru.li.java.basic.http.sever;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.li.java.basic.http.sever.handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpServer {
    private final int port;
    private final Dispatcher dispatcher;
    private final Logger logger;
    private final ExecutorService threadPool;

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.logger = LogManager.getLogger(HttpServer.class.getName());
        this.threadPool = Executors.newFixedThreadPool(4);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                logger.info("Подключился клиент");
                threadPool.execute(() -> {
                    try {
                        logger.info("Задание добавлено в пул потоков");
                        new ClientHandler(this, socket);
                    } catch (IOException e) {
                        logger.error("Не удалось подключить клиента\n" + e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            logger.error("Не удалось запустить сервер\n" + e.getMessage());
        }
    }
}
