package ru.li.java.basic.http.sever.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.li.java.basic.http.sever.HttpRequest;
import ru.li.java.basic.http.sever.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler {
    private final HttpServer server;
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private final Logger logger;

    public ClientHandler(HttpServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.logger = LogManager.getLogger(ClientHandler.class.getName());

        new Thread(() -> {
            try {
                logger.info("Запущен отдельный поток на исполнение");
                processRequest();
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                disconnect();
                logger.info("Отключился клиент");
            }

        }).start();
    }

    private void processRequest() throws IOException {
        byte[] buffer = new byte[8192];
        int n = in.read(buffer);
        if (n < 0) {
            logger.error("Нет сообщения");
            return;
        }
        String rawRequest = new String(buffer, 0, n);
        HttpRequest httpRequest = new HttpRequest(rawRequest);
        server.getDispatcher().execute(httpRequest, out);
    }

    private void disconnect() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());

        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
