package ru.li.java.basic.http.sever;

import ru.li.java.basic.http.sever.handlers.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private final Map<String, RequestHandler> dispatcher;
    private final RequestHandler unknownRequestHandler;

    public Dispatcher() {
        this.dispatcher = new HashMap<>();
        this.dispatcher.put("GET /hello_world", new HelloWorldHandler());
        this.dispatcher.put("GET /add", new AddHandler());
        this.dispatcher.put("POST /body", new PostBodyDemoHandler());
        this.unknownRequestHandler = new UnknownRequestHandler();
    }

    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        if (!dispatcher.containsKey(httpRequest.getRoute())) {
            unknownRequestHandler.execute(httpRequest, out);
            return;
        }
        dispatcher.get(httpRequest.getRoute()).execute(httpRequest, out);
    }
}
