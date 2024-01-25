package ru.li.java.basic.http.sever.handlers;

import ru.li.java.basic.http.sever.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestHandler {
    void execute(HttpRequest httpRequest, OutputStream out) throws IOException;
}
