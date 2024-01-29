package ru.li.java.basic.http.sever;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;
    private String body;
    private final Logger logger;

    public String getBody() {
        return body;
    }

    public String getUri() {
        return uri;
    }

    public String getRoute() {
        return method + " " + uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpRequest(String rawRequest) {
        this.parameters = new HashMap<>();
        this.logger = LogManager.getLogger(HttpRequest.class.getName());
        parseRawRequest(rawRequest);
    }

    private void parseRawRequest(String rawRequest) {
        logger.info("Получен запрос: " + rawRequest);
        int startIndex = rawRequest.indexOf(" ");
        int endIndex = rawRequest.indexOf(" ", startIndex + 1);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        parseParameters();
        parseBody(rawRequest);
    }

    private void parseBody(String rawRequest) {
        if (method != HttpMethod.POST) {
            return;
        }
        String searchWord = "Content-Length: "; // ожидаем что будет этот заголовок
        int i = rawRequest.indexOf(searchWord);
        if (i < 0) {
            return;
        }
        int startIndex = rawRequest.indexOf("\r\n\r\n", i + searchWord.length());
        int contentLengthValue = Integer.parseInt(rawRequest.substring(i + searchWord.length(), startIndex));
        if (contentLengthValue == 0) {
            return;
        }
        this.body = rawRequest.substring(startIndex).trim();
        logger.info("Получено тело: " + body);
    }

    private void parseParameters() {
        if (uri.contains("?")) {
            int startIndex = uri.indexOf("?");
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
