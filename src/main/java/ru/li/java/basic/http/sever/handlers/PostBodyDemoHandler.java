package ru.li.java.basic.http.sever.handlers;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.li.java.basic.http.sever.HttpRequest;
import ru.li.java.basic.http.sever.intities.Product;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PostBodyDemoHandler implements RequestHandler {
    private final Logger logger;
    private final Gson gson;

    public PostBodyDemoHandler() {
        this.logger = LogManager.getLogger(PostBodyDemoHandler.class.getName());
        this.gson = new Gson();
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        Product product = gson.fromJson(httpRequest.getBody(), Product.class);
        logger.info("Создан объект: " + product);

        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<html><body><h1>Hello World!</h1></body></html>";
        out.write(response.getBytes(StandardCharsets.UTF_8));
        logger.info("Отправлен ответ: " + response);
    }
}
