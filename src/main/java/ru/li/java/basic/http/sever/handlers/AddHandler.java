package ru.li.java.basic.http.sever.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.li.java.basic.http.sever.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AddHandler implements RequestHandler {
    private final Logger logger;

    public AddHandler() {
        this.logger = LogManager.getLogger(AddHandler.class.getName());
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        int a = Integer.parseInt(httpRequest.getParameter("a"));
        int b = Integer.parseInt(httpRequest.getParameter("b"));
        String result = a + " + " + b + " = " + (a + b);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<html><body><h1>" + result + "</h1></body></html>";
        out.write(response.getBytes(StandardCharsets.UTF_8));
        logger.info("Отправлен ответ: " + response);
    }
}
