package ru.li.java.basic.http.sever.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.li.java.basic.http.sever.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UnknownRequestHandler implements RequestHandler {
    private final Logger logger;

    public UnknownRequestHandler() {
        this.logger = LogManager.getLogger(UnknownRequestHandler.class.getName());
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        String response = "HTTP/1.1 404";
        out.write(response.getBytes(StandardCharsets.UTF_8));
        logger.info("Отправлен ответ: " + response);
    }
}
