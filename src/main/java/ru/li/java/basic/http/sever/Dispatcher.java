package ru.li.java.basic.http.sever;

import ru.li.java.basic.http.sever.handlers.AddHandler;
import ru.li.java.basic.http.sever.handlers.HelloWorldHandler;
import ru.li.java.basic.http.sever.handlers.RequestHandler;
import ru.li.java.basic.http.sever.handlers.UnknownRequestHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private final Map<String, RequestHandler> dispatcher;
    private final RequestHandler unknownRequestHandler;

    public Dispatcher() {
        this.dispatcher = new HashMap<>();
        this.dispatcher.put("/hello_world", new HelloWorldHandler());
        this.dispatcher.put("/add", new AddHandler());
        this.unknownRequestHandler = new UnknownRequestHandler();
    }

    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        if (!dispatcher.containsKey(httpRequest.getUri())) {
            unknownRequestHandler.execute(httpRequest, out);
            return;
        }
        dispatcher.get(httpRequest.getUri()).execute(httpRequest, out);
    }
}
