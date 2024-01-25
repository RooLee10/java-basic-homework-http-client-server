package ru.li.java.basic.http.sever.handlers;

import ru.li.java.basic.http.sever.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UnknownRequestHandler implements RequestHandler {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=utf-8\r\n\r\n<html><body><h1>Неизвестный запрос</h1></body></html>";
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
