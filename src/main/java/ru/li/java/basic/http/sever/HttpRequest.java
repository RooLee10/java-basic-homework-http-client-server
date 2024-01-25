package ru.li.java.basic.http.sever;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpRequest(String rawRequest) {
        this.parameters = new HashMap<>();
        parseRawRequest(rawRequest);
    }

    private void parseRawRequest(String rawRequest) {
        int startIndex = rawRequest.indexOf(" ");
        int endIndex = rawRequest.indexOf(" ", startIndex + 1);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        if (uri.contains("?")) {
            startIndex = uri.indexOf("?");
            String[] params = uri.substring(startIndex + 1).split("&");
            for (String param : params) {
                String[] p = param.split("=");
                this.parameters.put(p[0], p[1]);
            }
            this.uri = uri.substring(0, startIndex);
        }
    }

    public void printInfo() {
        System.out.println("-----------------------------------------------");
        System.out.println(uri);
        System.out.println(method);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
